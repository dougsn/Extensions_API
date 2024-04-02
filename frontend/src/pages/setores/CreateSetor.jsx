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
import { useState } from "react";
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";

const CreateSetorFormSchema = yup.object().shape({
  nome: yup
    .string()
    .required("O nome do setor é obrigatório")
    .max(50, "O nome deve ter no máximo 50 caracteres"),
});

export const CreateSetor = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateSetorFormSchema),
  });

  const handleCreateSetorFormSchema = async (data) => {
    const newSetor = {
      nome: data.nome.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/setor/v1", newSetor, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.status == 201) {
        toast({
          title: "Setor criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/setor`);
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

  return (
    <Box
      as="form"
      flex="1"
      borderRadius={8}
      p={["6", "8"]}
      onSubmit={handleSubmit(handleCreateSetorFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Setor
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Nome"
            label="Nome"
            {...register("nome")}
            error={formState.errors.nome}
          />
        </SimpleGrid>
      </VStack>

      <Flex mt="8" justify="flex-end">
      <HStack spacing="4">
          <Box>
            <VoltarButtonPopUp endpoint={"/setor"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
