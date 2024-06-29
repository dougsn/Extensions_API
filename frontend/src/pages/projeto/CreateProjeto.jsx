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
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";
import { useEffect, useState } from "react";
import { CommonSelect } from "../../components/Form/CommonSelect";
import { CommonTextarea } from "../../components/Form/CommonTextarea";

const CreateProjetoFormSchema = yup.object().shape({
  nome: yup.string().required("O nome é obrigatório"),
  descricao: yup.string().required("A descrição é obrigatória"),
  id_status: yup.string().required("O status é obrigatório"),
});

export const CreateProjeto = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [status, setStatus] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateProjetoFormSchema),
  });

  const getStatus = async () => {
    try {
      const request = await api.get(`/status/v1`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setStatus(request.data);
    } catch (error) {
      return null;
    }
  };

  const handleCreateProjetoFormSchema = async (data) => {
    const newProjeto = {
      nome: data.nome.trim(),
      descricao: data.descricao,
      id_status: data.id_status.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/projeto/v1", newProjeto, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Projeto criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/projeto`);
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
    getStatus();
  }, []);

  return (
    <Box
      as="form"
      flex="1"
      borderRadius={8}
      p={["6", "8"]}
      onSubmit={handleSubmit(handleCreateProjetoFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Projeto
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

          <CommonSelect
            entity={status}
            placeholder="Selecione um status"
            label={"Status"}
            {...register("id_status")}
            error={formState.errors.id_status}
          />
        </SimpleGrid>
      </VStack>
      <VStack pt={5} spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonTextarea
            height={"md"}
            label="Descrição"
            {...register("descricao")}
            error={formState.errors.descricao}
          />
        </SimpleGrid>
      </VStack>

      <Flex mt="8" justify="flex-end">
        <HStack spacing="4">
          <Box>
            <VoltarButtonPopUp endpoint={"/projeto"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
