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

export const ListImpressora = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [impressora, setImpressora] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getImpressora = async () => {
    try {
      const request = await api.get(`/impressora/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page.totalPages);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setImpressora(request.data._embedded.impressoraDTOList);
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
    setImpressora(newEntity._embedded.impressoraDTOList);
    handleSelectIsLoading(false);
  };

  const handleInputChange = (newEntity) => {
    handleSelectIsLoading(true);
    if (
      newEntity._embedded &&
      newEntity._embedded.impressoraDTOList &&
      newEntity._embedded.impressoraDTOList.length !== 0
    ) {
      setImpressora(newEntity._embedded.impressoraDTOList);
    } else {
      setImpressora(newEntity);
    }

    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
    getImpressora();
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
            Lista de Impressoras
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
                onClick={() => navigate("/impressora/new/")}
              >
                <Icon as={RiAddLine} fontSize="20" />
              </Button>
            )}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Impressoras
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
                onClick={() => navigate("/impressora/new/")}
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
          endpoint={"impressora"}
          sortPropertie={"marca"}
          placeholder="Filtrar Marca"
          label="Marca"
        />
        <CommonSelectChange
          handleLoading={handleSelectIsLoading}
          handleChange={handleSelectChange}
          entity={setor}
          endpoint={"impressora"}
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
            Cadastre uma nova impressora
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
            Falha ao obter dados das impressoras
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {impressora.map((impressoraMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={impressoraMap.id}>
                <CardHeader>
                  <Heading size="md">Marca: {impressoraMap.marca}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Marca: {impressoraMap.marca}</Text>
                  <Text>Modelo: {impressoraMap.modelo}</Text>
                  <Text>Ip: {impressoraMap.ip}</Text>
                  <Text>Tonner: {impressoraMap.tonner}</Text>
                  <Text>Observação: {impressoraMap.observacao}</Text>
                  <Text>Setor: {impressoraMap.nome_setor}</Text>
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
                          navigate(`/impressora/update/${impressoraMap.id}`)
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
                          navigate(`/impressora/delete/${impressoraMap.id}`)
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
              <Th>Marca</Th>
              <Th>Modelo</Th>
              <Th>Ip</Th>
              <Th>Setor</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {impressora.map((impressoraMap) => {
              return (
                <Tr key={impressoraMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{impressoraMap.marca}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{impressoraMap.modelo}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{impressoraMap.ip}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {impressoraMap.nome_setor}
                        </Text>
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
                              navigate(`/impressora/update/${impressoraMap.id}`)
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
                              navigate(`/impressora/delete/${impressoraMap.id}`)
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
