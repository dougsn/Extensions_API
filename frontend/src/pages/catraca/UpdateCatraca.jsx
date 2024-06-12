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

const UpdateCatracaFormSchema = yup.object().shape({
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

export const UpdateCatraca = () => {
  const [catraca, setCatraca] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateCatracaFormSchema),
  });

  const handleUpdateCatraca = async (data) => {
    const newCatraca = {
      id: id,
      nome: data.nome.trim(),
      com: data.com.trim(),
      mac: data.mac.trim(),
      numero_do_equipamento: data.numero_do_equipamento.trim(),
      ip: data.ip.trim(),
      numero_de_serie: data.numero_de_serie.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/catraca/v1", newCatraca, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "Catraca atualizada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/catraca"), 1000);
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

  const getCatracaById = async () => {
    try {
      const request = await api.get(`/catraca/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setCatraca(request.data);
        setValue("nome", request.data.nome);
        setValue("com", request.data.com);
        setValue("mac", request.data.mac);
        setValue("ip", request.data.ip);
        setValue("numero_do_equipamento", request.data.numero_do_equipamento);
        setValue("numero_de_serie", request.data.numero_de_serie);
      }
      setTimeout(() => {
        setIsLoading(false);
      }, 100);
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getCatracaById();
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
            Falha ao obter dados da catraca
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
          onSubmit={handleSubmit(handleUpdateCatraca)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Catraca: {catraca.nome}
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

          <VStack spacing="8">
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

          <VStack spacing="8">
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
              <CreateButtonWithSubmit isLoadingBtn={isLoadingBtn} />
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
