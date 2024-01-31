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

export const ListLocal = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [local, setLocal] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getTipoAntena = async () => {
    try {
      const request = await api.get(`/local/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setLocal(request.data._embedded.localDTOList);
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
    getTipoAntena();
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
            Lista de Locais
          </Heading>
          {userData.permissions.some(
            (p) => p.description === "ADMIN" || p.description === "MANAGER"
          ) && (
            <Button
              size="sm"
              fontSize="sm"
              colorScheme="blue"
              onClick={() => navigate("/antena/local/new/")}
            >
              <Icon as={RiAddLine} fontSize="20" />
            </Button>
          )}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Locais
          </Heading>
          {userData.permissions.some(
            (p) => p.description === "ADMIN" || p.description === "MANAGER"
          ) && (
            <Button
              size="sm"
              fontSize="sm"
              colorScheme="blue"
              leftIcon={<Icon as={RiAddLine} fontSize="20" />}
              onClick={() => navigate("/antena/local/new/")}
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
            Cadastre um novo local
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
            Falha ao obter dados dos locais
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {local.map((localMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={localMap.id}>
                <CardHeader>
                  <Heading size="md">Nome: {localMap.nome}</Heading>
                </CardHeader>
                <CardFooter justify="space-around">
                  {userData.permissions.some(
                    (p) =>
                      p.description === "ADMIN" || p.description === "MANAGER"
                  ) && (
                    <Button
                      size="sm"
                      fontSize="sm"
                      colorScheme="yellow"
                      color="white"
                      onClick={() =>
                        navigate(`/antena/local/update/${localMap.id}`)
                      }
                    >
                      <Icon as={RiEditLine} fontSize="20" />
                    </Button>
                  )}
                  {userData.permissions.some(
                    (p) =>
                      p.description === "ADMIN" || p.description === "MANAGER"
                  ) && (
                    <Button
                      size="sm"
                      fontSize="sm"
                      colorScheme="red"
                      color="white"
                      onClick={() =>
                        navigate(`/antena/local/delete/${localMap.id}`)
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
              <Th>Nome</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {local.map((localMap) => {
              return (
                <Tr key={localMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{localMap.nome}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <HStack spacing="2" display="flex" justifyContent="end">
                      {userData.permissions.some(
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
                            navigate(`/antena/local/update/${localMap.id}`)
                          }
                        >
                          <Icon as={RiEditLine} fontSize="20" />
                        </Button>
                      )}

                      {userData.permissions.some(
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
                            navigate(`/antena/local/delete/${localMap.id}`)
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
        lastPages={infoPage.totalPages}
        size={infoPage.size}
        totalElements={infoPage.totalElements}
        currentPage={page}
        onPageChange={setPage}
      />
    </Box>
  );
};
