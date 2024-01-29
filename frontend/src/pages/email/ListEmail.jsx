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

export const ListEmail = () => {
  const [page, setPage] = useState(0);
  const [infoPage, setInfopage] = useState(0);

  const [email, setEmail] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);
  const [isEmpty, setIsEmpty] = useState(false);

  const [isLargerThan800] = useMediaQuery("(max-width: 800px)");
  const { userData } = useContext(AuthenticationContext);

  const navigate = useNavigate();
  const toast = useToast();

  const getEmail = async () => {
    try {
      const request = await api.get(`/email/v1?page=${page}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setInfopage(request.data.page);
      if (request.data.page.totalElements == 0) {
        setIsEmpty(true);
      }
      setIsLoading(false);
      setEmail(request.data._embedded.emailDTOList);
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
    setEmail(newEntity._embedded.emailDTOList);
    handleSelectIsLoading(false);
  };

  const handleInputChange = (newEntity) => {
    handleSelectIsLoading(true);
    if (
      newEntity._embedded &&
      newEntity._embedded.emailDTOList &&
      newEntity._embedded.emailDTOList.length !== 0
    ) {
      setEmail(newEntity._embedded.emailDTOList);
    } else {
      setEmail(newEntity);
    }

    handleSelectIsLoading(false);
  };

  const handleSelectIsLoading = (loading) => {
    setIsLoading(loading);
  };

  useEffect(() => {
    getEmail();
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
            Lista de E-mails
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
                onClick={() => navigate("/email/new/")}
              >
                <Icon as={RiAddLine} fontSize="20" />
              </Button>
            )}
        </Flex>
      ) : (
        <Flex mb="8" justify="space-between" align="center">
          <Heading size="lg" fontWeight="500">
            Lista de E-mails
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
                onClick={() => navigate("/email/new/")}
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
          endpoint={"email"}
          sortPropertie={"conta"}
          placeholder="Filtrar conta"
          label="Conta"
        />
        <CommonSelectChange
          handleLoading={handleSelectIsLoading}
          handleChange={handleSelectChange}
          entity={setor}
          endpoint={"email"}
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
            Cadastre um novo e-mail
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
          {email.map((emailMap) => {
            return (
              <Card textAlign={"center"} w={"auto"} key={emailMap.id}>
                <CardHeader>
                  <Heading size="md">Conta: {emailMap.conta}</Heading>
                </CardHeader>
                <CardBody>
                  <Text>Senha: {emailMap.senha}</Text>
                  <Text>Setor: {emailMap.nome_setor}</Text>
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
                        onClick={() => navigate(`/email/update/${emailMap.id}`)}
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
                        onClick={() => navigate(`/email/delete/${emailMap.id}`)}
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
              <Th>Conta</Th>
              <Th>Senha</Th>
              <Th>Setor</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {email.map((emailMap) => {
              return (
                <Tr key={emailMap.id}>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{emailMap.conta}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{emailMap.senha}</Text>
                      </ChakraLink>
                    </Box>
                  </Td>
                  <Td>
                    <Box>
                      <ChakraLink>
                        <Text fontWeight="bold">{emailMap.nome_setor}</Text>
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
                              navigate(`/email/update/${emailMap.id}`)
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
                              navigate(`/email/delete/${emailMap.id}`)
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
