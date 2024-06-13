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
import { CreateButton } from "../../components/Button/CreateButton";
import { UpdateButton } from "../../components/Button/UpdateButton";
import { DeleteButton } from "../../components/Button/DeleteButton";
import { CommonInputChangeDefeitoManutencao } from "../../components/Form/CommonInputChangeDefeitoManutencao";
import { CommonSelectChangeUtilsManutencao } from "../../components/Form/CommonSelectChangeUtilsManutencao";
import DatePicker from "../../components/Calendar/DatePicker";

export const ListManutencaoCatraca = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [manutencaoCatraca, setManutencaoCatraca] = useState([]);
  const [catraca, setCatraca] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const toast = useToast();

  const getManutencaoCatraca = async () => {
    try {
      const request = await api.get(
        `/manutencao-catraca/v1?page=${page}&size=${5}&direction=desc`,
        {
          headers: { Authorization: `Bearer ${getToken()}` },
        }
      );
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setManutencaoCatraca(request.data._embedded.manutencaoCatracaDTOList);
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

  const getCatraca = async () => {
    try {
      const request = await api.get(`/catraca/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setIsLoading(false);
      setCatraca(request.data);
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
      newEntity._embedded.manutencaoCatracaDTOList &&
      newEntity._embedded.manutencaoCatracaDTOList.length !== 0
    ) {
      setManutencaoCatraca(newEntity._embedded.manutencaoCatracaDTOList);
    } else {
      setManutencaoCatraca(newEntity);
    }
    handleSelectIsLoading(false);
  };
  const handleSelectChange = (newEntity) => {
    handleSelectIsLoading(true);
    setManutencaoCatraca(newEntity);
    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
    getManutencaoCatraca();
    getCatraca();
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
            Lista de Manutenções
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/manutencao-catraca/new"} />}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Manutenções
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/manutencao-catraca/new"} />}
        </Flex>
      )}
      {isLargerThan800 ? (
        <>
          <Flex mb="8" justify="space-between" align="center" gap={50}>
            <DatePicker
              handleLoading={handleSelectIsLoading}
              handleChange={handleInputChange}
              endpoint={"manutencao-catraca"}
            />
            <CommonInputChangeDefeitoManutencao
              handleLoading={handleSelectIsLoading}
              handleChange={handleInputChange}
              endpoint={"manutencao-catraca"}
              placeholder="Filtrar Defeito"
              label="Defeito"
            />
            <CommonSelectChangeUtilsManutencao
              handleLoading={handleSelectIsLoading}
              handleChange={handleSelectChange}
              entity={catraca}
              subEndpoint={"catraca"}
              endpoint={"manutencao-catraca"}
              placeholder="Selecione uma catraca"
              label={"Catraca"}
            />
          </Flex>
        </>
      ) : (
        <Flex mb="8" justify="space-between" align="center" gap={50}>
          <DatePicker
            handleLoading={handleSelectIsLoading}
            handleChange={handleInputChange}
            endpoint={"manutencao-catraca"}
          />
          <CommonInputChangeDefeitoManutencao
            handleLoading={handleSelectIsLoading}
            handleChange={handleInputChange}
            endpoint={"manutencao-catraca"}
            placeholder="Filtrar Defeito"
            label="Defeito"
          />
          <CommonSelectChangeUtilsManutencao
            handleLoading={handleSelectIsLoading}
            handleChange={handleSelectChange}
            entity={catraca}
            subEndpoint={"catraca"}
            endpoint={"manutencao-catraca"}
            placeholder="Selecione uma catraca"
            label={"Catraca"}
          />
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
            Cadastre uma nova manutenção
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
            Falha ao obter dados das manutenções
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {manutencaoCatraca.map((manutencaoCatracaMap) => {
            return (
              <Card
                textAlign={"center"}
                w={"auto"}
                key={manutencaoCatracaMap.id}
              >
                <CardHeader>
                  <Heading size="md">Dia: {manutencaoCatracaMap.dia}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Defeito: {manutencaoCatracaMap.defeito}</Text>
                  <Text>Procedimento: {manutencaoCatracaMap.observacao}</Text>
                  <Text>Catraca: {manutencaoCatracaMap.nome_catraca}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <UpdateButton
                        endpoint={`/manutencao-catraca/update/${manutencaoCatracaMap.id}`}
                      />
                    )}
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <DeleteButton
                        endpoint={`/manutencao-catraca/delete/${manutencaoCatracaMap.id}`}
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
              <Th>Dia</Th>
              <Th>Defeito</Th>
              <Th>Procedimento</Th>
              <Th>Catraca</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {manutencaoCatraca.map((manutencaoCatracaMap) => {
              return (
                <Tr key={manutencaoCatracaMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {manutencaoCatracaMap.dia}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {manutencaoCatracaMap.defeito}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {manutencaoCatracaMap.observacao}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {manutencaoCatracaMap.nome_catraca}
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
                          <UpdateButton
                            endpoint={`/manutencao-catraca/update/${manutencaoCatracaMap.id}`}
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
                            endpoint={`/manutencao-catraca/delete/${manutencaoCatracaMap.id}`}
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
