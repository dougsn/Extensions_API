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

const CreateWifiFormSchema = yup.object().shape({
  ip: yup.string().required("O ip é obrigatório"),
  usuario: yup.string().required("O usuário é obrigatório"),
  senha_browser: yup.string().required("A senha do browser é obrigatória"),
  ssid: yup.string().required("O ssid é obrigatório"),
  senha_wifi: yup.string().required("A senha do wifi é obrigatória"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const CreateWifi = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false);
  const [setor, setSetor] = useState([]);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateWifiFormSchema),
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

  const handleCreateWifiFormSchema = async (data) => {
    const newWifi = {
      ip: data.ip.trim(),
      usuario: data.usuario.trim(),
      senha_browser: data.senha_browser.trim(),
      ssid: data.ssid.trim(),
      senha_wifi: data.senha_wifi.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post("/wifi/v1", newWifi, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 201) {
        toast({
          title: "WI-FI criado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/wifi`);
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
      onSubmit={handleSubmit(handleCreateWifiFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar WI-FI
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
            placeholder="Usuário"
            label="Usuário"
            {...register("usuario")}
            error={formState.errors.usuario}
          />
          <CommonInput
            placeholder="Senha do Browser"
            label="Senha do Browser"
            {...register("senha_browser")}
            error={formState.errors.senha_browser}
          />
        </SimpleGrid>
      </VStack>
      <VStack pt={5} spacing="8">
        <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
          <CommonInput
            placeholder="SSID"
            label="SSID"
            {...register("ssid")}
            error={formState.errors.ssid}
          />
          <CommonInput
            placeholder="Senha do WI-FI"
            label="Senha do WI-FI"
            {...register("senha_wifi")}
            error={formState.errors.senha_wifi}
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
            <VoltarButtonPopUp endpoint={"/wifi"} />
          </Box>
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
