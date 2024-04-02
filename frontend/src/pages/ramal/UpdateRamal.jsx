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

const UpdateRamalFormSchema = yup.object().shape({
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

export const UpdateRamal = () => {
  const [ramal, setRamal] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateRamalFormSchema),
  });

  const handleUpdateRamal = async (data) => {
    const newRamal = {
      id: id,
      nome: data.nome.trim(),
      ramal: data.ramal.trim(),
      email: data.email.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/funcionario/v1", newRamal, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "Ramal atualizado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/ramal"), 1000);
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

  const getRamalById = async () => {
    try {
      const request = await api.get(`/funcionario/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setRamal(request.data);
        setValue("nome", request.data.nome);
        setValue("ramal", request.data.ramal);
        setValue("email", request.data.email);
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
    getRamalById();
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
            Falha ao obter dados do ramal
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
          onSubmit={handleSubmit(handleUpdateRamal)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Ramal do: {ramal.nome}
          </Heading>

          <Divider my="6" borderColor="gray.300" />

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Ramal"
                label="Nome do Ramal"
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
              <CreateButtonWithSubmit isLoadingBtn={isLoadingBtn} />
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
