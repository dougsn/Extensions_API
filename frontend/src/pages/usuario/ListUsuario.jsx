import { useContext, useEffect, useState } from "react";
import { api } from "../../services/api";
import { useNavigate } from "react-router-dom";

import {
  Box,
  Flex,
  Heading,
  Button,
  Icon,
  Table,
  Thead,
  Tr,
  Th,
  Td,
  Tbody,
  Link as ChakraLink,
  Text,
  HStack,
  Spinner,
  Alert,
  AlertIcon,
  AlertTitle,
  AlertDescription,
  useToast,
  useMediaQuery,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
} from "@chakra-ui/react";
import { RiAddLine, RiDeleteBinLine, RiEditLine } from "react-icons/ri";
import { RxMagnifyingGlass } from "react-icons/rx";
import { getToken } from "../../utils/localstorage";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { Pagination } from "../../components/Pagination";

export const UsuarioList = () => {
  const [page, setPage] = useState(0);
  const [lastPage, setLastPage] = useState(0);

  const [usuario, setUsuario] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getUsuario = async () => {
    try {
      const request = await api.get(`/user?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setLastPage(request.headers["x-total-pages"]);
      setIsLoading(false);
      setUsuario(request.data);
      if (request.data.length === 0) {
        setIsEmpty(true);
      }
    } catch (error) {
      setIsLoading(false);
      setErro(true);
      toast({
        title: error.response.data.error,
        status: "error",
        position: "top-right",
        duration: 2000,
        isClosable: true,
      });
      return null;
    }
  };

  useEffect(() => {
    getUsuario();
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  }, [page]);

  return (
    <Box display={"flex"} flexDirection={"column"} p="8" w={"100%"}>
      {isLargerThan800 ? (
        <Flex mb="8" justify="space-around" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Usuários
          </Heading>
          {userData.role == "ADMIN" && (
            <Button
              size="sm"
              fontSize="sm"
              colorScheme="blue"
              onClick={() => navigate("/user/new/")}
            >
              <Icon as={RiAddLine} fontSize="20" />
            </Button>
          )}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Usuários
          </Heading>
          {userData.role == "ADMIN" && (
            <Button
              size="sm"
              fontSize="sm"
              colorScheme="blue"
              leftIcon={<Icon as={RiAddLine} fontSize="20" />}
              onClick={() => navigate("/user/new/")}
            >
              Criar novo
            </Button>
          )}
        </Flex>
      )}

      {isLoading ? (
        <Flex
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          height="400px"
        >
          <Spinner
            size="xl"
            speed=".45s"
            emptyColor="gray.200"
            color="blue.500"
          />
        </Flex>
      ) : isEmpty ? (
        <Alert
          status="info"
          variant="subtle"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          height="400px"
          borderRadius={"xl"}
          colorScheme="blue"
        >
          <AlertIcon boxSize="40px" mr={0} />
          <AlertTitle mt={4} mb={1} fontSize="xl">
            Não há dados
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Cadastre um novo tanque
          </AlertDescription>
        </Alert>
      ) : erro ? (
        <Alert
          status="error"
          variant="subtle"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          height="400px"
          borderRadius={"xl"}
          colorScheme="blue"
        >
          <AlertIcon boxSize="40px" mr={0} />
          <AlertTitle mt={4} mb={1} fontSize="xl">
            Falha ao obter dados dos usuários
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {usuario.map((usuarioMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={usuarioMap.id}>
                <CardHeader>
                  <Heading size="md">
                    Usuário: {usuarioMap.login}
                  </Heading>
                </CardHeader>
                <CardBody>
                  <Text>Matrícula: {usuarioMap.matricula}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  <Button
                    size="sm"
                    fontSize="sm"
                    colorScheme="blue"
                    onClick={() =>
                      navigate(`/user/detail/${usuarioMap.id}`)
                    }
                  >
                    <Icon as={RxMagnifyingGlass} fontSize="20" />
                  </Button>
                  {userData.role == "ADMIN" && (
                    <Button
                      size="sm"
                      fontSize="sm"
                      colorScheme="yellow"
                      color="white"
                      onClick={() =>
                        navigate(`/user/update/${usuarioMap.id}`)
                      }
                    >
                      <Icon as={RiEditLine} fontSize="20" />
                    </Button>
                  )}
                  {userData.role == "ADMIN" && (
                    <Button
                      size="sm"
                      fontSize="sm"
                      colorScheme="red"
                      color="white"
                      onClick={() =>
                        navigate(`/user/delete/${usuarioMap.id}`)
                      }
                    >
                      <Icon as={RiDeleteBinLine} fontSize="20" />
                    </Button>
                  )}
                </CardFooter>
              </Card>
            );
          })}
        </Box>
      ) : (
        <Table colorScheme="blackAlpha">
          <Thead>
            <Tr>
              <Th px={["4", "4", "6"]} width="8">
                #
              </Th>
              <Th>Nome</Th>
              <Th>Matrícula</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {usuario.map((usuarioMap) => {
              return (
                <Tr key={usuarioMap.id}>
                  <Td px={["4", "4", "6"]} fontWeight="bold">
                    {usuarioMap.id}
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{usuarioMap.login}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{usuarioMap.matricula}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <HStack spacing="2" display="flex" justifyContent="end">
                      <Button
                        size="sm"
                        fontSize="sm"
                        colorScheme="blue"
                        onClick={() =>
                          navigate(`/user/detail/${usuarioMap.id}`)
                        }
                      >
                        <Icon as={RxMagnifyingGlass} fontSize="20" />
                      </Button>

                      {userData.role == "ADMIN" && (
                        <Button
                          size="sm"
                          fontSize="sm"
                          colorScheme="yellow"
                          color="white"
                          onClick={() =>
                            navigate(`/user/update/${usuarioMap.id}`)
                          }
                        >
                          <Icon as={RiEditLine} fontSize="20" />
                        </Button>
                      )}

                      {userData.role == "ADMIN" && (
                        <Button
                          size="sm"
                          fontSize="sm"
                          colorScheme="red"
                          color="white"
                          onClick={() =>
                            navigate(`/user/delete/${usuarioMap.id}`)
                          }
                        >
                          <Icon as={RiDeleteBinLine} fontSize="20" />
                        </Button>
                      )}
                    </HStack>
                  </Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      )}
      <Pagination
        lastPages={lastPage}
        currentPage={page}
        onPageChange={setPage}
      />
    </Box>
  );
};