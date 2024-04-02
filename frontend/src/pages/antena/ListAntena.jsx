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
import { CommonInputChange } from "../../components/Form/CommonInputChange";
import { CommonSelectChangeUtils } from "../../components/Form/CommonSelectChangeUtils";
import { getToken } from "../../utils/localstorage";
import { CreateButton } from "../../components/Button/CreateButton";
import { UpdateButton } from "../../components/Button/UpdateButton";
import { DeleteButton } from "../../components/Button/DeleteButton";

export const ListAntena = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [antena, setAntena] = useState([]);
  const [local, setLocal] = useState([]);
  const [modelo, setModelo] = useState([]);
  const [tipoAntena, setTipoAntena] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getAntena = async () => {
    try {
      const request = await api.get(`/antena/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setAntena(request.data._embedded.antenaDTOList);
    } catch (error) {
      console.log(error);
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

  const getLocal = async () => {
    try {
      const request = await api.get(`/local/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setLocal(request.data);
    } catch (error) {
      return null;
    }
  };

  const getModelo = async () => {
    try {
      const request = await api.get(`/modelo/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setModelo(request.data);
    } catch (error) {
      return null;
    }
  };

  const getTipoAntena = async () => {
    try {
      const request = await api.get(`/tipo-antena/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setTipoAntena(request.data);
    } catch (error) {
      return null;
    }
  };

  const handleSelectChange = (newEntity) => {
    handleSelectIsLoading(true);
    setAntena(newEntity._embedded.antenaDTOList);
    handleSelectIsLoading(false);
  };

  const handleInputChange = (newEntity) => {
    handleSelectIsLoading(true);
    if (
      newEntity._embedded &&
      newEntity._embedded.antenaDTOList &&
      newEntity._embedded.antenaDTOList.length !== 0
    ) {
      setAntena(newEntity._embedded.antenaDTOList);
    } else {
      setAntena(newEntity);
    }
    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
    getAntena();
    getLocal();
    getModelo();
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
            Lista de Antenas
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/antena/new"} />}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Antenas
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/antena/new"} />}
        </Flex>
      )}

      {isLargerThan800 ? (
        <>
          <Flex mb="8" justify="space-between" align="center" gap={50}>
            <CommonInputChange
              handleLoading={handleSelectIsLoading}
              handleChange={handleInputChange}
              endpoint={"antena"}
              sortPropertie={"ssid"}
              placeholder="Filtrar SSID"
              label="SSID"
            />
            <CommonSelectChangeUtils
              handleLoading={handleSelectIsLoading}
              handleChange={handleSelectChange}
              entity={local}
              subEndpoint={"local"}
              endpoint={"antena"}
              placeholder="Selecione um local"
              label={"Local"}
            />
          </Flex>
          <Flex mb="8" justify="space-between" align="center" gap={50}>
            <CommonSelectChangeUtils
              handleLoading={handleSelectIsLoading}
              handleChange={handleSelectChange}
              entity={modelo}
              subEndpoint={"modelo"}
              endpoint={"antena"}
              placeholder="Selecione um modelo"
              label={"Modelo"}
            />

            <CommonSelectChangeUtils
              handleLoading={handleSelectIsLoading}
              handleChange={handleSelectChange}
              entity={tipoAntena}
              subEndpoint={"tipo-antena"}
              endpoint={"antena"}
              placeholder="Selecione um tipo"
              label={"Tipo"}
            />
          </Flex>
        </>
      ) : (
        <Flex mb="8" justify="space-between" align="center" gap={50}>
          <CommonInputChange
            handleLoading={handleSelectIsLoading}
            handleChange={handleInputChange}
            endpoint={"antena"}
            sortPropertie={"ssid"}
            placeholder="Filtrar SSID"
            label="SSID"
          />
          <CommonSelectChangeUtils
            handleLoading={handleSelectIsLoading}
            handleChange={handleSelectChange}
            entity={local}
            subEndpoint={"local"}
            endpoint={"antena"}
            placeholder="Selecione um local"
            label={"Local"}
          />

          <CommonSelectChangeUtils
            handleLoading={handleSelectIsLoading}
            handleChange={handleSelectChange}
            entity={modelo}
            subEndpoint={"modelo"}
            endpoint={"antena"}
            placeholder="Selecione um modelo"
            label={"Modelo"}
          />

          <CommonSelectChangeUtils
            handleLoading={handleSelectIsLoading}
            handleChange={handleSelectChange}
            entity={tipoAntena}
            subEndpoint={"tipo-antena"}
            endpoint={"antena"}
            placeholder="Selecione um tipo"
            label={"Tipo"}
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
            Cadastre uma nova antena
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
            Falha ao obter dados das antenas
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {antena.map((antenaMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={antenaMap.id}>
                <CardHeader>
                  <Heading size="md">Nome: {antenaMap.ssid}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>IP: {antenaMap.ip}</Text>
                  <Text>SSID: {antenaMap.ssid}</Text>
                  <Text>Localização: {antenaMap.localizacao}</Text>
                  <Text>Senha: {antenaMap.senha}</Text>
                  <Text>Local: {antenaMap.nome_local}</Text>
                  <Text>Modelo: {antenaMap.nome_modelo}</Text>
                  <Text>Tipo de Antena: {antenaMap.nome_tipo_antena}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <UpdateButton
                        endpoint={`/antena/update/${antenaMap.id}`}
                      />
                    )}
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <DeleteButton
                        endpoint={`/antena/delete/${antenaMap.id}`}
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
              <Th>Localização</Th>
              <Th>Local</Th>
              <Th>IP</Th>
              <Th>SSID</Th>
              <Th>Senha</Th>
              <Th>Modelo</Th>
              <Th>Tipo</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {antena.map((antenaMap) => {
              return (
                <Tr key={antenaMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{antenaMap.localizacao}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{antenaMap.nome_local}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{antenaMap.ip}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{antenaMap.ssid}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{antenaMap.senha}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{antenaMap.nome_modelo}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {antenaMap.nome_tipo_antena}
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
                            endpoint={`/antena/update/${antenaMap.id}`}
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
                            endpoint={`/antena/delete/${antenaMap.id}`}
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
