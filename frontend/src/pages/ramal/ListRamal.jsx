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

export const ListRamal = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [ramal, setRamal] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getRamal = async () => {
    try {
      const request = await api.get(`/funcionario/v1?page=${page}&size=${5}`);
      setInfopage(request.data.page.totalPages);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setRamal(request.data._embedded.funcionarioDTOList);
    } catch (error) {
      setIsLoading(false);
      setErro(true);
      toast({
        title: error.response.data.errorMessage,
        status: "error",
        position: "top-right",
        duration: 2000,
        isClosable: true,
      });
      return null;
    }
  };

  useEffect(() => {
    getRamal();
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
            Lista de Ramal
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && (
              <Button
                size="sm"
                fontSize="sm"
                colorScheme="blue"
                onClick={() => navigate("/ramal/new/")}
              >
                <Icon as={RiAddLine} fontSize="20" />
              </Button>
            )}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Ramal
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && (
              <Button
                size="sm"
                fontSize="sm"
                colorScheme="blue"
                leftIcon={<Icon as={RiAddLine} fontSize="20" />}
                onClick={() => navigate("/ramal/new/")}
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
            Cadastre um novo ramal
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
            Falha ao obter dados dos ramais
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {ramal.map((ramalMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={ramalMap.id}>
                <CardHeader>
                  <Heading size="md">Nome: {ramalMap.nome}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Setor: {ramalMap.nome_setor}</Text>
                  <Text>Ramal: {ramalMap.ramal}</Text>
                  <Text>Email: {ramalMap.email}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <Button
                        size="sm"
                        fontSize="sm"
                        colorScheme="yellow"
                        color="white"
                        onClick={() => navigate(`/ramal/update/${ramalMap.id}`)}
                      >
                        <Icon as={RiEditLine} fontSize="20" />
                      </Button>
                    )}
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <Button
                        size="sm"
                        fontSize="sm"
                        colorScheme="red"
                        color="white"
                        onClick={() => navigate(`/ramal/delete/${ramalMap.id}`)}
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
              <Th>Nome</Th>
              <Th>Setor</Th>
              <Th>Ramal</Th>
              <Th>Email</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {ramal.map((ramalMap) => {
              return (
                <Tr key={ramalMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{ramalMap.nome}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{ramalMap.nome_setor}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{ramalMap.ramal}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{ramalMap.email}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <HStack spacing="2" display="flex" justifyContent="end">
                      {Object.keys(userData).length != 0 &&
                        userData &&
                        userData.permissions &&
                        userData.permissions.some(
                          (p) =>
                            p.description === "ADMIN" ||
                            p.description === "MANAGER"
                        ) && (
                          <Button
                            size="sm"
                            fontSize="sm"
                            colorScheme="yellow"
                            color="white"
                            onClick={() =>
                              navigate(`/ramal/update/${ramalMap.id}`)
                            }
                          >
                            <Icon as={RiEditLine} fontSize="20" />
                          </Button>
                        )}

                      {Object.keys(userData).length != 0 &&
                        userData &&
                        userData.permissions &&
                        userData.permissions.some(
                          (p) =>
                            p.description === "ADMIN" ||
                            p.description === "MANAGER"
                        ) && (
                          <Button
                            size="sm"
                            fontSize="sm"
                            colorScheme="red"
                            color="white"
                            onClick={() =>
                              navigate(`/ramal/delete/${ramalMap.id}`)
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
        lastPages={infoPage}
        currentPage={page}
        onPageChange={setPage}
      />
    </Box>
  );
};
