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

const CreateImpressoraFormSchema = yup.object().shape({
  marca: yup
    .string()
    .required("A marca é obrigatório")
    .max(20, "A marca deve ter no máximo 20 caracteres"),
  modelo: yup
    .string()
    .required("O modelo é obrigatório")
    .max(20, "O modelo deve ter no máximo 20 caracteres"),
  ip: yup
    .string()
    .required("O ip é obrigatório")
    .max(50, "O ip deve ter no máximo 50 caracteres"),
  tonner: yup.string().required("O tonner é obrigatório"),
  observacao: yup
    .string()
    .required("A observação é obrigatório")
    .max(15, "A observação deve ter no máximo 15 caracteres"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const CreateImpressora = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [setor, setSetor] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateImpressoraFormSchema),
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

  const handleCreateImpressoraFormSchema = async (data) => {
    const newImpressora = {
      marca: data.marca.trim(),
      modelo: data.modelo.trim(),
      ip: data.ip.trim(),
      tonner: data.tonner.trim(),
      observacao: data.observacao.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/impressora/v1", newImpressora, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Impressora criada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/impressora`);
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
      onSubmit={handleSubmit(handleCreateImpressoraFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Impressora
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Marca"
            label="Marca"
            {...register("marca")}
            error={formState.errors.marca}
          />
          <CommonInput
            placeholder="Modelo"
            label="Modelo"
            {...register("modelo")}
            error={formState.errors.modelo}
          />
        </SimpleGrid>
      </VStack>

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Ip"
            label="Ip"
            {...register("ip")}
            error={formState.errors.ip}
          />
          <CommonInput
            placeholder="Tonner"
            label="Tonner"
            {...register("tonner")}
            error={formState.errors.tonner}
          />
        </SimpleGrid>
      </VStack>

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="Observação"
            label="Observação"
            {...register("observacao")}
            error={formState.errors.observacao}
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
            <VoltarButtonPopUp endpoint={"/impressora"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
