import {
  Box,
  Button,
  Flex,
  HStack,
  Heading,
  SimpleGrid,
  VStack,
  useToast,
} from "@chakra-ui/react";

import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { CommonInput } from "../../components/Form/CommonInput";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import { api } from "../../services/api";
import { deleteToken, getToken, setToken } from "../../utils/localstorage";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { CommonInputPassword } from "../../components/Form/CommonInputPassword";

const LoginUser = yup.object().shape({
  username: yup.string().required("Nome obrigatório"),
  password: yup.string().required("Senha é obrigatória"),
});

export const LoginForm = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(LoginUser),
  });

  const { setUserData, setIsAuthenticated } = useContext(AuthenticationContext);

  const loginUser = async (data) => {
    const loginData = {
      username: data.username.trim(),
      password: data.password.trim(),
    };
    setIsLoading(true);

    try {
      const response = await api.post("/auth/v1/login", loginData, {
        "Content-Type": "application/json",
      });
      if (response.status == 200) {
        toast({
          title: "Login realizado com sucesso",
          status: "success",
          position: "top-right",
          duration: 1500,
          isClosable: true,
        });

        setToken(response.data.token);

        await getUserData();
        setTimeout(() => {
          navigate("/ramais");
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
        title: error.response.data.error.errorMessage,
        status: "error",
        position: "top-right",
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const getUserData = async () => {
    const userToken = getToken();

    if (userToken) {
      try {
        const response = await api.get("/auth/v1", {
          headers: { Authorization: `Bearer ${userToken}` },
        });

        if (response.status == 200) {
          setUserData(response.data);
          setTimeout(() => {
            setIsAuthenticated(true);
          }, 1000);
        }
      } catch {
        deleteToken();
        setIsAuthenticated(false);
      }
    } else {
      deleteToken();
      setIsAuthenticated(false);
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
      onSubmit={handleSubmit(loginUser)}
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
          Entrar no Sistema
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
        <Flex mt="8">
          <HStack>
            <Button
              type="submit"
              p={"0 100px"}
              colorScheme="messenger"
              isLoading={isLoading}
            >
              Entrar
            </Button>
          </HStack>
        </Flex>
      </Box>
    </Box>
  );
};
