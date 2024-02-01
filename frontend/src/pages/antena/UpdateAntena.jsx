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

const UpdateAntenaFormSchema = yup.object().shape({
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

export const UpdateAntena = () => {
  const [antena, setAntena] = useState([]);
  const [local, setLocal] = useState([]);
  const [modelo, setModelo] = useState([]);
  const [tipoAntena, setTipoAntena] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateAntenaFormSchema),
  });

  const handleUpdateAntena = async (data) => {
    const newAntena = {
      id: id,
      ip: data.ip.trim(),
      localizacao: data.localizacao.trim(),
      ssid: data.ssid.trim(),
      senha: data.senha.trim(),
      id_local: data.id_local.trim(),
      id_modelo: data.id_modelo.trim(),
      id_tipo_antena: data.id_tipo_antena.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/antena/v1", newAntena, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "Antena atualizada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/antena"), 1000);
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

  const getAntenaById = async () => {
    try {
      const request = await api.get(`/antena/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setAntena(request.data);
        setValue("ip", request.data.ip);
        setValue("localizacao", request.data.localizacao);
        setValue("ssid", request.data.ssid);
        setValue("senha", request.data.senha);
        setValue("id_local", request.data.id_local);
        setValue("id_modelo", request.data.id_modelo);
        setValue("id_tipo_antena", request.data.id_tipo_antena);
      }
      setTimeout(() => {
        setIsLoading(false);
      }, 100);
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

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

  useEffect(() => {
    getAntenaById();
    getLocal();
    getModelo();
    getTipoAntena();
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
            Falha ao obter dados da antena
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
          onSubmit={handleSubmit(handleUpdateAntena)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Antena: {antena.ssid}
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
                <Button
                  colorScheme="blackAlpha"
                  onClick={() => navigate("/antena")}
                >
                  Voltar
                </Button>
              </Box>
              <Button
                type="submit"
                colorScheme="messenger"
                isLoading={isLoadingBtn}
              >
                Salvar
              </Button>
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
