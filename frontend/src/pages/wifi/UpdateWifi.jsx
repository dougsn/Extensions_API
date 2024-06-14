import {
  Alert,
  AlertDescription,
  AlertIcon,
  AlertTitle,
  Box,
  Button,
  Divider,
  Flex,
  HStack,
  Heading,
  SimpleGrid,
  Spinner,
  VStack,
  useToast,
} from "@chakra-ui/react";

import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { CommonInput } from "../../components/Form/CommonInput";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/api";
import { useEffect, useState } from "react";
import { getToken } from "../../utils/localstorage";
import { CommonSelect } from "../../components/Form/CommonSelect";
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";

const UpdateWifiFormSchema = yup.object().shape({
  usuario: yup.string().required("O usuário é obrigatório"),
  senha_browser: yup.string().required("A senha do browser é obrigatória"),
  ssid: yup.string().required("O ssid é obrigatório"),
  senha_wifi: yup.string().required("A senha do wifi é obrigatória"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const UpdateWifi = () => {
  const [wifi, setWifi] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateWifiFormSchema),
  });

  const handleUpdateWifi = async (data) => {
    const newWifi = {
      id: id,
      ip: data.ip.trim(),
      usuario: data.usuario.trim(),
      senha_browser: data.senha_browser.trim(),
      ssid: data.ssid.trim(),
      senha_wifi: data.senha_wifi.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/wifi/v1", newWifi, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "WI-FI atualizado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/wifi"), 1000);
      }
    } catch (error) {
      setIsLoadingBtn(false);

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

  const getWifiById = async () => {
    try {
      const request = await api.get(`/wifi/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setWifi(request.data);
        setValue("ip", request.data.ip);
        setValue("usuario", request.data.usuario);
        setValue("ssid", request.data.ssid);
        setValue("senha_browser", request.data.senha_browser);
        setValue("senha_wifi", request.data.senha_wifi);
        setValue("id_setor", request.data.id_setor);
      }
      setTimeout(() => {
        setIsLoading(false);
      }, 100);
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

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

  useEffect(() => {
    getWifiById();
    getSetor();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {isLoading ? (
        <Box
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          width={"100%"}
        >
          <Flex
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
            textAlign="center"
            height="400px"
          >
            <Spinner
              size="xl"
              speed=".45s"
              emptyColor="gray.200"
              color="blue.500"
            />
          </Flex>
        </Box>
      ) : erro ? (
        <Alert
          status="error"
          variant="subtle"
          flex="1"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          textAlign="center"
          height="400px"
          borderRadius={"xl"}
          colorScheme="blue"
        >
          <AlertIcon boxSize="40px" mr={0} />
          <AlertTitle mt={4} mb={1} fontSize="xl">
            Falha ao obter dados do WI-FI
          </AlertTitle>
          <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
            Tente novamente mais tarde.
          </AlertDescription>
        </Alert>
      ) : (
        <Box
          as="form"
          flex="1"
          borderRadius={8}
          p={["6", "8"]}
          onSubmit={handleSubmit(handleUpdateWifi)}
        >
          <Heading size="lg" fontWeight="500">
            Editar WI-FI: {wifi.ssid}
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
              <CreateButtonWithSubmit isLoadingBtn={isLoadingBtn} />
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
