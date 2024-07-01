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
  Badge,
} from "@chakra-ui/react";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { Pagination } from "../../components/Pagination";
import { CommonSelectChangeUtilsList } from "../../components/Form/CommonSelectChangeUtilsList";
import { CommonInputChangeLike } from "../../components/Form/CommonInputChangeLike";
import { getToken } from "../../utils/localstorage";
import { CreateButton } from "../../components/Button/CreateButton";
import { UpdateButton } from "../../components/Button/UpdateButton";
import { DeleteButton } from "../../components/Button/DeleteButton";
import { DetailButton } from "../../components/Button/DetailButton";
import { CommonInputChange } from "../../components/Form/CommonInputChange";

export const ListProjeto = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [projeto, setProjeto] = useState([]);
  const [status, setStatus] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const toast = useToast();

  const getProjeto = async () => {
    try {
      const request = await api.get(
        `/projeto/v1?page=${page}&size=${5}&direction=desc`,
        {
          headers: { Authorization: `Bearer ${getToken()}` },
        }
      );
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setProjeto(request.data._embedded.projetoDTOList);
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

  const getStatus = async () => {
    try {
      const request = await api.get("/status/v1", {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.data.length === 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setStatus(request.data);
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

  const handleInputChange = (newEntity) => {
    handleSelectIsLoading(true);
    if (
      newEntity._embedded &&
      newEntity._embedded.projetoDTOList &&
      newEntity._embedded.projetoDTOList.length !== 0
    ) {
      setProjeto(newEntity._embedded.projetoDTOList);
    } else {
      setProjeto(newEntity);
    }

    handleSelectIsLoading(false);
  };

  const handleSelectChange = (newEntity) => {
    handleSelectIsLoading(true);
    if (
      newEntity._embedded &&
      newEntity._embedded.projetoDTOList &&
      newEntity._embedded.projetoDTOList.length !== 0
    ) {
      setProjeto(newEntity._embedded.projetoDTOList);
    } else {
      setProjeto(newEntity);
    }
    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  function renderStatus(status) {
    if (status == "Criado") {
      return (
        <Badge variant="solid" colorScheme="teal">
          Criado
        </Badge>
      );
    } else if (status == "Pausado") {
      return (
        <Badge variant="solid" colorScheme="yellow">
          Pausado
        </Badge>
      );
    } else if (status == "Em andamento") {
      return (
        <Badge variant="solid" colorScheme="blue">
          Em andamento
        </Badge>
      );
    } else if (status == "Finalizado") {
      return (
        <Badge variant="solid" colorScheme="green">
          Finalizado
        </Badge>
      );
    }
  }

  useEffect(() => {
    getProjeto();
    getStatus();
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
            Lista de Projetos
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/projeto/new"} />}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Projetos
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/projeto/new"} />}
        </Flex>
      )}
      <Flex mb="8" justify="space-between" align="center" gap={50}>
        <CommonInputChangeLike
          handleLoading={handleSelectIsLoading}
          handleChange={handleInputChange}
          endpoint={"projeto"}
          subEndpoint={"nome"}
          sortPropertie={"nome"}
          placeholder="Filtrar nome"
          label="Nome"
        />
        <CommonSelectChangeUtilsList
          handleLoading={handleSelectIsLoading}
          handleChange={handleSelectChange}
          entity={status}
          endpoint={"projeto"}
          subEndpoint={"status"}
          placeholder="Selecione um status"
          label={"Status"}
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
            Cadastre um novo projeto
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
            Falha ao obter dados dos projetos
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {projeto.map((projetoMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={projetoMap.id}>
                <CardHeader>
                  <Heading size="md">Nome: {projetoMap.nome}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Status: {projetoMap.nome_status}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <UpdateButton
                        endpoint={`/projeto/update/${projetoMap.id}`}
                      />
                    )}
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <DeleteButton
                        endpoint={`/projeto/delete/${projetoMap.id}`}
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
              <Th>Nome</Th>
              <Th>Status</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {projeto.map((projetoMap) => {
              return (
                <Tr key={projetoMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{projetoMap.nome}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>{renderStatus(projetoMap.nome_status)}</Box>
                  </Td>
                  <Td>
                    <HStack spacing="2" display="flex" justifyContent="end">
                      <DetailButton
                        endpoint={`/projeto/detail/${projetoMap.id}`}
                      />
                      {Object.keys(userData).length != 0 &&
                        userData &&
                        userData.permissions &&
                        userData.permissions.some(
                          (p) =>
                            p.description === "ADMIN" ||
                            p.description === "MANAGER"
                        ) && (
                          <UpdateButton
                            endpoint={`/projeto/update/${projetoMap.id}`}
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
