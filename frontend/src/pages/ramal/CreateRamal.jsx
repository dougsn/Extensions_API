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

const CreateRamalFormSchema = yup.object().shape({
  nome: yup
    .string()
    .required("O nome é obrigatório")
    .max(100, "O nome deve ter no máximo 100 caracteres"),
  ramal: yup
    .string()
    .required("O ramal é obrigatório")
    .max(7, "O ramal deve ter no máximo 7 caracteres"),
  email: yup
    .string()
    .required("O email é obrigatório")    
    .email("Digite um e-mail válido")
    .max(100, "O e-mail deve ter no máximo 100 caracteres"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const CreateRamal = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [setor, setSetor] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateRamalFormSchema),
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

  const handleCreateSetorFormSchema = async (data) => {
    const newRamal = {
      nome: data.nome.trim(),
      ramal: data.ramal.trim(),
      email: data.email.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/funcionario/v1", newRamal, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Ramal criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/ramal`);
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
      onSubmit={handleSubmit(handleCreateSetorFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Ramal
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
          <CommonInput
            placeholder="Ramal"
            label="Ramal"
            {...register("ramal")}
            error={formState.errors.ramal}
          />
        </SimpleGrid>
      </VStack>
      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Email"
            label="Email"
            {...register("email")}
            error={formState.errors.email}
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
            <VoltarButtonPopUp endpoint={"/ramal"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
