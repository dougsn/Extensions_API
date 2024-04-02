import {
  Box,
  Button,
  Flex,
  HStack,
  Heading,
  SimpleGrid,
  Text,
  VStack,
  useToast,
} from "@chakra-ui/react";

import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { CommonInput } from "../../components/Form/CommonInput";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { api } from "../../services/api";
import { setToken } from "../../utils/localstorage";
import { CommonInputPassword } from "../../components/Form/CommonInputPassword";
import { LoginButton } from "../../components/Button/LoginButton";

const RegisterUser = yup.object().shape({
  username: yup.string().required("Nome obrigatório"),
  password: yup.string().required("Senha é obrigatória"),
});

export const RegisterForm = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(RegisterUser),
  });

  const registerUser = async (data) => {
    const registerData = {
      username: data.username.trim(),
      password: data.password.trim(),
      permissions: [{ id: 2 }], // Permissão de Manager para poderem manipular os dados no app.
    };
    setIsLoading(true);

    try {
      const response = await api.post("/auth/v1/register", registerData, {
        "Content-Type": "application/json",
      });
      if (response.status == 201) {
        toast({
          title: "Registro realizado com sucesso",
          status: "success",
          position: "top-right",
          duration: 1500,
          isClosable: true,
        });

        setTimeout(() => {
          navigate("/");
        }, 1000);
      }
    } catch (error) {
      setIsLoading(false);

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

      if (error.response.data.status == 401) {
        toast({
          title: error.response.data.errorMessage,
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

  return (
    <Box
      as="form"
      flex="1"
      display={"flex"}
      flexDirection={"column"}
      alignItems={"center"}
      mt="20"
      borderRadius={8}
      p={["10px", "8"]}
      onSubmit={handleSubmit(registerUser)}
    >
      <Box
        alignContent={"center"}
        display={"flex"}
        flexDirection={"column"}
        alignItems={"center"}
        gap={10}
        borderRadius={8}
        borderWidth={1}
        boxShadow="lg"
        p={["6", "8"]}
      >
        <Heading size="lg" fontWeight="500">
          Criar conta
        </Heading>

        <VStack spacing="8">
          <SimpleGrid spacing={["6", "8"]} w="100%">
            <CommonInput
              label="Usuário"
              placeholder="Usuário"
              {...register("username")}
              error={formState.errors.username}
            />
            <CommonInputPassword
              label="Senha"
              placeholder="Senha"
              {...register("password")}
              error={formState.errors.password}
            />
          </SimpleGrid>
        </VStack>
        <Box>
          <Text as={Link} to={"/"}>
            Entrar no sistema
          </Text>
        </Box>
        <Flex mt="8">
          <HStack>
            <LoginButton isLoadingBtn={isLoading} value={"Registrar"} />
          </HStack>
        </Flex>
      </Box>
    </Box>
  );
};
