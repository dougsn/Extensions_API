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
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";

const CreateComputadorFormSchema = yup.object().shape({
  hostname: yup
    .string()
    .required("O hostname é obrigatório")
    .max(50, "O hostname deve ter no máximo 50 caracteres"),
  cpu: yup
    .string()
    .required("A cpu é obrigatório")
    .max(15, "A cpu deve ter no máximo 15 caracteres"),
  disco: yup
    .string()
    .required("O disco é obrigatório")
    .max(15, "O disco deve ter no máximo 15 caracteres"),
  memoria: yup
    .string()
    .required("A memória é obrigatório")
    .max(15, "A memória deve ter no máximo 15 caracteres"),
  modelo: yup
    .string()
    .required("O modelo é obrigatório")
    .max(15, "O modelo deve ter no máximo 15 caracteres"),
  observacao: yup
    .string()
    .required("A observação é obrigatório")
    .max(15, "A observação deve ter no máximo 15 caracteres"),
  sistema_operacional: yup
    .string()
    .required("O sistema_operacional é obrigatório")
    .max(15, "O sistema_operacional deve ter no máximo 15 caracteres"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const CreateComputador = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [setor, setSetor] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateComputadorFormSchema),
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

  const handleCreateComputadorFormSchema = async (data) => {
    const newComputador = {
      hostname: data.hostname.trim(),
      cpu: data.cpu.trim(),
      disco: data.disco.trim(),
      memoria: data.memoria.trim(),
      modelo: data.modelo.trim(),
      observacao: data.observacao.trim(),
      sistema_operacional: data.sistema_operacional.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/computador/v1", newComputador, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Computador criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/computador`);
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
      onSubmit={handleSubmit(handleCreateComputadorFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar E-mail
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Hostname"
            label="Hostname"
            {...register("hostname")}
            error={formState.errors.hostname}
          />
          <CommonInput
            placeholder="Cpu"
            label="Cpu"
            {...register("cpu")}
            error={formState.errors.cpu}
          />
        </SimpleGrid>
      </VStack>

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Disco"
            label="Disco"
            {...register("disco")}
            error={formState.errors.disco}
          />
          <CommonInput
            placeholder="Memória"
            label="Memória"
            {...register("memoria")}
            error={formState.errors.memoria}
          />
        </SimpleGrid>
      </VStack>

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Modelo"
            label="Modelo"
            {...register("modelo")}
            error={formState.errors.modelo}
          />
          <CommonInput
            placeholder="Observação"
            label="Observação"
            {...register("observacao")}
            error={formState.errors.observacao}
          />
        </SimpleGrid>
      </VStack>

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Sistema Operacional"
            label="Sistema Operacional"
            {...register("sistema_operacional")}
            error={formState.errors.sistema_operacional}
          />
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
            <VoltarButtonPopUp endpoint={"/computador"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
