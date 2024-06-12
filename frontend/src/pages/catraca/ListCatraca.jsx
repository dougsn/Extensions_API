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

export const ListCatraca = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [catraca, setCatraca] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const toast = useToast();

  const getCatraca = async () => {
    try {
      const request = await api.get(`/catraca/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setCatraca(request.data._embedded.catracaDTOList);
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

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
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
            Lista de Catracas
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/catraca/new"} />}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de Catracas
          </Heading>
          {Object.keys(userData).length != 0 &&
            userData &&
            userData.permissions &&
            userData.permissions.some(
              (p) => p.description === "ADMIN" || p.description === "MANAGER"
            ) && <CreateButton endpoint={"/catraca/new"} />}
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
            Cadastre uma nova catraca
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
            Falha ao obter dados das catracas
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : isLargerThan800 ? (
        <Box display={"flex"} flexDirection={"column"} gap={10}>
          {catraca.map((catracaMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={catracaMap.id}>
                <CardHeader>
                  <Heading size="md">Marca: {catracaMap.nome}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Ip: {catracaMap.ip}</Text>
                  <Text>
                    Número do Equipamento: {catracaMap.numero_do_equipamento}
                  </Text>
                  <Text>Número de Série: {catracaMap.numero_de_serie}</Text>
                  <Text>COM: {catracaMap.com}</Text>
                  <Text>MAC: {catracaMap.mac}</Text>
                </CardBody>
                <CardFooter justify="space-around">
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <UpdateButton
                        endpoint={`/catraca/update/${catracaMap.id}`}
                      />
                    )}
                  {Object.keys(userData).length != 0 &&
                    userData.permissions.some(
                      (p) =>
                        p.description === "ADMIN" || p.description === "MANAGER"
                    ) && (
                      <DeleteButton
                        endpoint={`/catraca/delete/${catracaMap.id}`}
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
              <Th>Ip</Th>
              <Th>Número do Equipamento</Th>
              <Th>Número de Série</Th>
              <Th>COM</Th>
              <Th>MAC</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {catraca.map((catracaMap) => {
              return (
                <Tr key={catracaMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{catracaMap.nome}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{catracaMap.ip}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {catracaMap.numero_do_equipamento}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {catracaMap.numero_de_serie}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {catracaMap.com}
                        </Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">
                          {catracaMap.mac}
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
                            endpoint={`/catraca/update/${catracaMap.id}`}
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
                            endpoint={`/catraca/delete/${catracaMap.id}`}
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
