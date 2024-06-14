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

const CreateTerminalFormSchema = yup.object().shape({
  usuario: yup.string().required("O usuário é obrigatório"),
  modelo: yup.string().required("O modelo é obrigatório"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const CreateTerminal = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [setor, setSetor] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateTerminalFormSchema),
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

  const handleCreateTerminalFormSchema = async (data) => {
    const newTerminal = {
      usuario: data.usuario.trim(),
      modelo: data.modelo.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/terminal/v1", newTerminal, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Terminal criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/terminal`);
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
      onSubmit={handleSubmit(handleCreateTerminalFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Terminal
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Usuário"
            label="Usuário"
            {...register("usuario")}
            error={formState.errors.usuario}
          />
          <CommonInput
            placeholder="Modelo"
            label="Modelo"
            {...register("modelo")}
            error={formState.errors.modelo}
          />
        </SimpleGrid>
      </VStack>
      <VStack pt={5} spacing="8">
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
            <VoltarButtonPopUp endpoint={"/terminal"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
