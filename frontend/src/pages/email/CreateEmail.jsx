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
import { getToken } from "../../utils/localstorage";
import { useEffect, useState } from "react";
import { CommonSelect } from "../../components/Form/CommonSelect";

const CreateEmailFormSchema = yup.object().shape({
  conta: yup
    .string()
    .required("A conta é obrigatório")
    .email("Digite um e-mail válido")
    .max(100, "A conta deve ter no máximo 100 caracteres"),
  senha: yup
    .string()
    .required("A senha é obrigatório")
    .max(50, "A senha deve ter no máximo 50 caracteres"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const CreateEmail = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [setor, setSetor] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateEmailFormSchema),
  });

  const getSetor = async () => {
    try {
      const request = await api.get(`/setor/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setSetor(request.data);
    } catch (error) {
      return null;
    }
  };

  const handleCreateEmailFormSchema = async (data) => {
    const newEmail = {
      conta: data.conta.trim(),
      senha: data.senha.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/email/v1", newEmail, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Email criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/email`);
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
        title: error.response.data.errorMessage,
        status: "error",
        position: "top-right",
        duration: 3000,
        isClosable: true,
      });
    }
  };
  useEffect(() => {
    getSetor();
  }, []);

  return (
    <Box
      as="form"
      flex="1"
      borderRadius={8}
      p={["6", "8"]}
      onSubmit={handleSubmit(handleCreateEmailFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar E-mail
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Conta"
            label="Conta"
            {...register("conta")}
            error={formState.errors.conta}
          />
          <CommonInput
            placeholder="Senha"
            label="Senha"
            {...register("senha")}
            error={formState.errors.senha}
          />
        </SimpleGrid>
      </VStack>
      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonSelect
            entity={setor}
            placeholder="Selecione um setor"
            label={"Setor"}
            {...register("id_setor")}
            error={formState.errors.id_setor}
          />
        </SimpleGrid>
      </VStack>

      <Flex mt="8" justify="flex-end">
        <HStack spacing="4">
          <Box>
            <Button colorScheme="blackAlpha" onClick={() => navigate("/email")}>
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
