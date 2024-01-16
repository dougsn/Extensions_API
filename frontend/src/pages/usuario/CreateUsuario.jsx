import {
  Box,
  Button,
  Divider,
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
import { api } from "../../services/api";
import { CommonInputPassword } from "../../components/Form/CommonInputPassword";
import { CommonSelectEnum } from "../../components/Form/CommonSelectEnum";
import { getToken } from "../../utils/localstorage";
import { useState } from "react";

const CreateUserFormSchema = yup.object().shape({
  username: yup.string().required("O nome do usuário é obrigatório"),
  password: yup.string().required("Senha é obrigatória"),
  permissions: yup.string().required("O nível de acesso é obrigatório"),
});

export const CreateUsuario = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);

  const userLevel = [ // Criar 1 método para buscar as roles e popular automaticamente.
    { value: 1, label: "Administrador" },
    { value: 2, label: "Gerente" },
    { value: 3, label: "Usuário" },
  ];

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateUserFormSchema),
  });

  const handleCreateUserFormSchema = async (data) => {
    const newUser = {
      username: data.username.trim(),
      password: data.password.trim(),
      permissions: [{ id: data.permissions }],
    };
    setIsLoading(true);

    try {
      const request = await api.post("auth/v1/register", newUser, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      console.log(newUser);

      if (request.status == 201) {
        toast({
          title: "Usuário criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/user`);
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

      toast({
        title: error.response.data.error,
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
      borderRadius={8}
      p={["6", "8"]}
      onSubmit={handleSubmit(handleCreateUserFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Usuário
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Login"
            label="Login"
            {...register("username")}
            error={formState.errors.username}
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
            <Button colorScheme="blackAlpha" onClick={() => navigate("/user")}>
              Voltar
            </Button>
          </Box>
          <Button type="submit" colorScheme="messenger" isLoading={isLoading}>
            Salvar
          </Button>
        </HStack>
      </Flex>
    </Box>
  );
};
