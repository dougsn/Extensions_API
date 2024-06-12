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

const CreateCatracaFormSchema = yup.object().shape({
  nome: yup
    .string()
    .required("O nome é obrigatório")
    .max(60, "O nome deve ter no máximo 60 caracteres"),
  com: yup
    .string()
    .required("A COM é obrigatório")
    .max(60, "A COM deve ter no máximo 60 caracteres"),
  mac: yup
    .string()
    .required("O MAC é obrigatório")
    .max(60, "O MAC deve ter no máximo 60 caracteres"),
  numero_do_equipamento: yup
    .string()
    .required("O número do equipamento é obrigatório")
    .max(60, "O número do equipamento deve ter no máximo 60 caracteres"),
  ip: yup
    .string()
    .required("O ip é obrigatório")
    .max(50, "O ip deve ter no máximo 50 caracteres"),
  numero_de_serie: yup
    .string()
    .required("O número de sére é obrigatório")
    .max(60, "O número de sére deve ter no máximo 60 caracteres"),
});

export const CreateCatraca = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateCatracaFormSchema),
  });

  const handleCreateCatracaFormSchema = async (data) => {
    const newCatraca = {
      nome: data.nome.trim(),
      com: data.com.trim(),
      mac: data.mac.trim(),
      numero_do_equipamento: data.numero_do_equipamento.trim(),
      ip: data.ip.trim(),
      numero_de_serie: data.numero_de_serie.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/catraca/v1", newCatraca, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Catraca criada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/catraca`);
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
      onSubmit={handleSubmit(handleCreateCatracaFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Catraca
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
            placeholder="Ip"
            label="Ip"
            {...register("ip")}
            error={formState.errors.ip}
          />
        </SimpleGrid>
      </VStack>

      <VStack pt={5} spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="COM"
            label="COM"
            {...register("com")}
            error={formState.errors.com}
          />
          <CommonInput
            placeholder="MAC"
            label="MAC"
            {...register("mac")}
            error={formState.errors.mac}
          />
        </SimpleGrid>
      </VStack>

      <VStack pt={5} spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Número de Série"
            label="Número de Série"
            {...register("numero_de_serie")}
            error={formState.errors.numero_de_serie}
          />
          <CommonInput
            placeholder="Número do Equipamento"
            label="Número do Equipamento"
            {...register("numero_do_equipamento")}
            error={formState.errors.numero_do_equipamento}
          />
        </SimpleGrid>
      </VStack>

      <Flex mt="8" justify="flex-end">
        <HStack spacing="4">
          <Box>
            <VoltarButtonPopUp endpoint={"/catraca"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
