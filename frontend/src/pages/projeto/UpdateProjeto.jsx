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
import { CommonTextarea } from "../../components/Form/CommonTextarea";
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";
import { CommonSelectEnum } from "../../components/Form/CommonSelectEnum";
import { CommonSelect } from "../../components/Form/CommonSelect";

const UpdateProjetoFormSchema = yup.object().shape({
  nome: yup.string().required("O usuário é obrigatório"),
  descricao: yup.string().required("A descrição é obrigatória"),
  id_status: yup.string().required("O status é obrigatório"),
});

export const UpdateProjeto = () => {
  const [projeto, setProjeto] = useState([]);
  const [status, setStatus] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateProjetoFormSchema),
  });

  const getStatus = async () => {
    try {
      const request = await api.get(`/status/v1`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setStatus(request.data);
    } catch (error) {
      return null;
    }
  };

  const handleUpdateProjeto = async (data) => {
    const newProjeto = {
      id: id,
      nome: data.nome.trim(),
      descricao: data.descricao,
      id_status: data.id_status.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/projeto/v1", newProjeto, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "Projeto atualizado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/projeto"), 1000);
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

  const getProjetoById = async () => {
    try {
      const request = await api.get(`/projeto/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setProjeto(request.data);
        setValue("nome", request.data.nome);
        setValue("descricao", request.data.descricao);
        setValue("id_status", request.data.id_status);
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
    getProjetoById();
    getStatus();
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
            Falha ao obter dados do projeto
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
          onSubmit={handleSubmit(handleUpdateProjeto)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Projeto: {projeto.nome}
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

              <CommonSelect
                entity={status}
                placeholder="Selecione um status"
                label={"Status"}
                {...register("id_status")}
                error={formState.errors.id_status}
              />
            </SimpleGrid>
          </VStack>
          <VStack pt={5} spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonTextarea
                height={"md"}
                label="Descrição"
                {...register("descricao")}
                error={formState.errors.descricao}
              />
            </SimpleGrid>
          </VStack>
          <Flex mt="8" justify="flex-end">
            <HStack spacing="4">
              <Box>
                <VoltarButtonPopUp endpoint={"/projeto"} />
              </Box>
              <CreateButtonWithSubmit isLoadingBtn={isLoadingBtn} />
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
