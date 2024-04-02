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

const CreateAntenaFormSchema = yup.object().shape({
  ip: yup
    .string()
    .required("O ip é obrigatório")
    .max(60, "O ip deve ter no máximo 60 caracteres"),
  localizacao: yup
    .string()
    .required("A localizacao é obrigatório")
    .max(60, "A localizacao deve ter no máximo 60 caracteres"),
  ssid: yup
    .string()
    .required("O ssid é obrigatório")
    .max(60, "O ssid deve ter no máximo 60 caracteres"),
  senha: yup
    .string()
    .required("A senha é obrigatório")
    .max(60, "A senha deve ter no máximo 60 caracteres"),
  id_local: yup.string().required("O local é obrigatório"),
  id_modelo: yup.string().required("O modelo é obrigatório"),
  id_tipo_antena: yup.string().required("O tipo da antena é obrigatório"),
});

export const CreateAntena = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [local, setLocal] = useState([]);
  const [modelo, setModelo] = useState([]);
  const [tipoAntena, setTipoAntena] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateAntenaFormSchema),
  });

  const getLocal = async () => {
    try {
      const request = await api.get(`/local/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setLocal(request.data);
    } catch (error) {
      return null;
    }
  };

  const getModelo = async () => {
    try {
      const request = await api.get(`/modelo/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setModelo(request.data);
    } catch (error) {
      return null;
    }
  };

  const getTipoAntena = async () => {
    try {
      const request = await api.get(`/tipo-antena/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setTipoAntena(request.data);
    } catch (error) {
      return null;
    }
  };

  const handleCreateAntenaFormSchema = async (data) => {
    const newAntena = {
      ip: data.ip.trim(),
      localizacao: data.localizacao.trim(),
      ssid: data.ssid.trim(),
      senha: data.senha.trim(),
      id_local: data.id_local.trim(),
      id_modelo: data.id_modelo.trim(),
      id_tipo_antena: data.id_tipo_antena.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/antena/v1", newAntena, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "Antena criada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/antena`);
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
    getLocal();
    getModelo();
    getTipoAntena();
  }, []);

  return (
    <Box
      as="form"
      flex="1"
      borderRadius={8}
      p={["6", "8"]}
      onSubmit={handleSubmit(handleCreateAntenaFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Antena
      </Heading>

      <Divider my="6" borderColor="gray.300" />

      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="IP"
            label="IP"
            {...register("ip")}
            error={formState.errors.ip}
          />
          <CommonInput
            placeholder="Localização"
            label="Localização"
            {...register("localizacao")}
            error={formState.errors.localizacao}
          />
        </SimpleGrid>
      </VStack>
      <VStack spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="SSID"
            label="SSID"
            {...register("ssid")}
            error={formState.errors.ssid}
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
            entity={local}
            placeholder="Selecione um local"
            label={"Local"}
            {...register("id_local")}
            error={formState.errors.id_local}
          />
          <CommonSelect
            entity={modelo}
            placeholder="Selecione um modelo"
            label={"Modelo"}
            {...register("id_modelo")}
            error={formState.errors.id_modelo}
          />
          <CommonSelect
            entity={tipoAntena}
            placeholder="Selecione um tipo de antena"
            label={"Tipo de Antena"}
            {...register("id_tipo_antena")}
            error={formState.errors.id_tipo_antena}
          />
        </SimpleGrid>
      </VStack>

      <Flex mt="8" justify="flex-end">
        <HStack spacing="4">
          <Box>
            <VoltarButtonPopUp endpoint={"/antena"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
