import {
  Alert,
  AlertDescription,
  AlertIcon,
  AlertTitle,
  Box,
  Button,
  Divider,
  Flex,
  HStack,
  Heading,
  SimpleGrid,
  Spinner,
  VStack,
  useToast,
} from "@chakra-ui/react";

import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { CommonInput } from "../../components/Form/CommonInput";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/api";
import { useContext, useEffect, useState } from "react";
import { deleteToken, getToken } from "../../utils/localstorage";
import { CommonInputPassword } from "../../components/Form/CommonInputPassword";
import { CommonSelectEnum } from "../../components/Form/CommonSelectEnum";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";

const UpdateUserFormSchema = yup.object().shape({
  name: yup.string().required("O nome do usuário é obrigatório"),
});

export const UpdateUsuario = () => {
  const [usuario, setUsuario] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { userData, setIsAuthenticated, setUserData } = useContext(
    AuthenticationContext
  );

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const userLevel = [
    // Criar 1 método para buscar as roles e popular automaticamente.
    { value: 1, label: "Administrador" },
    { value: 2, label: "Gerente" },
    { value: 3, label: "Usuário" },
  ];

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateUserFormSchema),
  });

  const logoutUserUpdate = () => {
    setIsAuthenticated(false);
    setUserData({});
    deleteToken();
    toast({
      title: "Usuário atualizado com sucesso, faça login novamente!",
      status: "success",
      position: "top-right",
      duration: 1000,
      isClosable: true,
    });
    setTimeout(() => navigate("/"), 1000);
  };

  const handleUpdateUser = async (data) => {
    if (data.permissions == "") {
      // Se estiver vazio, use o valor do estado user
      data.permissions = usuario.permissions[0].id;
    }
    const newUser = {
      id: id,
      name: data.name.trim(),
      password: data.password.trim(),
      permissions: [{ id: data.permissions }],
    };

    setIsLoadingBtn(true);
    try {
      const request = await api.put("/user/v1", newUser, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200 && userData.id == id) {
        logoutUserUpdate();
        setTimeout(() => navigate("/"), 1000);
      } else if (request.status == 200) {
        toast({
          title: "Usuário atualizado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/user"), 1000);
      }
    } catch (error) {
      setIsLoadingBtn(false);

      if (error.message == "Network Error") {
        toast({
          title: "Serviço indisponível no momento, tento novamente mais tarde",
          status: "error",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        return false;
      }

      if (error.response.data.status == 400) {
        toast({
          title: "Revise os dados inseridos",
          status: "info",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        return false;
      }

      toast({
        title: error.response.data.errorMessage,
        status: "error",
        position: "top-right",
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const getUserById = async () => {
    try {
      const request = await api.get(`/user/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      console.log(request.data.permissions[0].id);
      if (request.length != 0) {
        setErro(false);
        setUsuario(request.data);
        setValue("name", request.data.name);
      }
      setTimeout(() => {
        setIsLoading(false);
      }, 100);
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getUserById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {isLoading ? (
        <Box
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          width={"100%"}
        >
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
        </Box>
      ) : erro ? (
        <Alert
          status="error"
          variant="subtle"
          flex="1"
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
            Falha ao obter dados do usuário
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : (
        <Box
          as="form"
          flex="1"
          borderRadius={8}
          p={["6", "8"]}
          onSubmit={handleSubmit(handleUpdateUser)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Usuário: {usuario.name}
          </Heading>

          <Divider my="6" borderColor="gray.300" />

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Usuário"
                label="Nome do Usuário"
                {...register("name")}
                error={formState.errors.name}
              />
              <CommonInputPassword
                placeholder="Senha"
                label="Senha"
                {...register("password")}
                error={formState.errors.password}
              />
            </SimpleGrid>
          </VStack>
          <VStack pt={5} spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonSelectEnum
                type={userLevel}
                label={"Nível de acesso"}
                {...register("permissions")}
                placeholder="Selecione um nível de acesso"
                error={formState.errors.permissions}
              />
            </SimpleGrid>
          </VStack>

          <Flex mt="8" justify="flex-end">
            <HStack spacing="4">
              <Box>
                <Button
                  colorScheme="blackAlpha"
                  onClick={() => navigate("/user")}
                >
                  Voltar
                </Button>
              </Box>
              <Button
                type="submit"
                colorScheme="messenger"
                isLoading={isLoadingBtn}
              >
                Salvar
              </Button>
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
