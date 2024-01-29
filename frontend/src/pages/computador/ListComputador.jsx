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
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { Pagination } from "../../components/Pagination";
import { CommonSelectChange } from "../../components/Form/CommonSelectChange";
import { CommonInputChange } from "../../components/Form/CommonInputChange";
import { getToken } from "../../utils/localstorage";

export const ListComputador = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [computador, setComputador] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getComputador = async () => {
    try {
      const request = await api.get(`/computador/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setComputador(request.data._embedded.computadorDTOList);
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
  const getSetor = async () => {
    try {
      const request = await api.get(`/setor/v1/all`, {});
      setSetor(request.data);
    } catch (error) {
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
  const handleSelectChange = (newEntity) => {
    handleSelectIsLoading(true);
    setComputador(newEntity._embedded.computadorDTOList);
    handleSelectIsLoading(false);
  };

  const handleInputChange = (newEntity) => {
    handleSelectIsLoading(true);
    if (
      newEntity._embedded &&
      newEntity._embedded.computadorDTOList &&
      newEntity._embedded.computadorDTOList.length !== 0
    ) {
      setComputador(newEntity._embedded.computadorDTOList);
    } else {
      setComputador(newEntity);
    }

    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
    getComputador();
    getSetor();
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
            Lista de Computadores
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
                onClick={() => navigate("/computador/new/")}
              >
                <Icon as={RiAddLine} fontSize="20" />
              </Button>
            )}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Computadores
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
                onClick={() => navigate("/computador/new/")}
              >
                Criar novo
              </Button>
            )}
        </Flex>
      )}
      <Flex mb="8" justify="space-between" align="center" gap={50}>
        <CommonInputChange
          handleLoading={handleSelectIsLoading}
          handleChange={handleInputChange}
          endpoint={"computador"}
          sortPropertie={"hostname"}
          placeholder="Filtrar Hostname"
          label="Hostname"
        />
        <CommonSelectChange
          handleLoading={handleSelectIsLoading}
          handleChange={handleSelectChange}
          entity={setor}
          endpoint={"computador"}
          placeholder="Selecione um setor"
          label={"Setor"}
        />
      </Flex>

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
            Cadastre um novo computador
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
            Falha ao obter dados dos computadores
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {computador.map((computadorMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={computadorMap.id}>
                <CardHeader>
                  <Heading size="md">
                    Hostname: {computadorMap.hostname}
                  </Heading>
                </CardHeader>
                <CardBody>
                  <Text>Hostname: {computadorMap.hostname}</Text>
                  <Text>Cpu: {computadorMap.cpu}</Text>
                  <Text>Memória: {computadorMap.memoria}</Text>
                  <Text>Disco: {computadorMap.disco}</Text>
                  <Text>Modelo: {computadorMap.modelo}</Text>
                  <Text>Observação: {computadorMap.observacao}</Text>
                  <Text>
                    Sistema Operacional: {computadorMap.sistema_operacional}
                  </Text>
                  <Text>Setor: {computadorMap.nome_setor}</Text>
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
                        onClick={() =>
                          navigate(`/computador/update/${computadorMap.id}`)
                        }
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
                        onClick={() =>
                          navigate(`/computador/delete/${computadorMap.id}`)
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
              <Th>Hostname</Th>
              <Th>Setor</Th>
              <Th>Cpu</Th>
              <Th>Memória</Th>
              <Th>Disco</Th>
              <Th>Modelo</Th>
              <Th>Sistema Operacional</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {computador.map((computadorMap) => {
              return (
                <Tr key={computadorMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{computadorMap.hostname}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {computadorMap.nome_setor}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{computadorMap.cpu}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{computadorMap.memoria}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{computadorMap.disco}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{computadorMap.modelo}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{computadorMap.sistema_operacional}</Text>
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
                              navigate(`/computador/update/${computadorMap.id}`)
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
                              navigate(`/computador/delete/${computadorMap.id}`)
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
