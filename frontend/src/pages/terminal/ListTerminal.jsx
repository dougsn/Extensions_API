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
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { Pagination } from "../../components/Pagination";
import { CommonSelectChangeUtilsList } from "../../components/Form/CommonSelectChangeUtilsList";
import { CommonInputChangeLike } from "../../components/Form/CommonInputChangeLike";
import { getToken } from "../../utils/localstorage";
import { CreateButton } from "../../components/Button/CreateButton";
import { UpdateButton } from "../../components/Button/UpdateButton";
import { DeleteButton } from "../../components/Button/DeleteButton";

export const ListTerminal = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [terminal, setTerminal] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const toast = useToast();

  const getTerminal = async () => {
    try {
      const request = await api.get(`/terminal/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setTerminal(request.data._embedded.terminalDTOList);
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
    if (
      newEntity._embedded &&
      newEntity._embedded.terminalDTOList &&
      newEntity._embedded.terminalDTOList.length !== 0
    ) {
      setTerminal(newEntity._embedded.terminalDTOList);
    } else {
      setTerminal(newEntity);
    }
    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
    getTerminal();
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
            Lista de Terminal
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/terminal/new"} />}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Terminal
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/terminal/new"} />}
        </Flex>
      )}
      <Flex mb="8" justify="space-between" align="center" gap={50}>
        <CommonSelectChangeUtilsList
          handleLoading={handleSelectIsLoading}
          handleChange={handleSelectChange}
          entity={setor}
          endpoint={"terminal"}
          subEndpoint={"setor"}
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
            Cadastre um novo Terminal
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
            Falha ao obter dados dos terminais
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {terminal.map((terminalMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={terminalMap.id}>
                <CardHeader>
                  <Heading size="md">Usuário: {terminalMap.usuario}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Modelo: {terminalMap.modelo}</Text>
                  <Text>Setor: {terminalMap.nome_setor}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <UpdateButton
                        endpoint={`/terminal/update/${terminalMap.id}`}
                      />
                    )}
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <DeleteButton
                        endpoint={`/terminal/delete/${terminalMap.id}`}
                      />
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
              <Th>Usuário</Th>
              <Th>Modelo</Th>
              <Th>Setor</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {terminal.map((terminalMap) => {
              return (
                <Tr key={terminalMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{terminalMap.usuario}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{terminalMap.modelo}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{terminalMap.nome_setor}</Text>
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
                          <UpdateButton
                            endpoint={`/terminal/update/${terminalMap.id}`}
                          />
                        )}

                      {Object.keys(userData).length != 0 &&
                        userData &&
                        userData.permissions &&
                        userData.permissions.some(
                          (p) =>
                            p.description === "ADMIN" ||
                            p.description === "MANAGER"
                        ) && (
                          <DeleteButton
                            endpoint={`/terminal/delete/${terminalMap.id}`}
                          />
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
