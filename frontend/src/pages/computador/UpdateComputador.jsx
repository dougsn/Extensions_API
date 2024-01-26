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

const UpdateComputadorFormSchema = yup.object().shape({
  hostname: yup
    .string()
    .required("O hostname é obrigatório")
    .max(50, "O hostname deve ter no máximo 50 caracteres"),
  cpu: yup
    .string()
    .required("A cpu é obrigatório")
    .max(15, "A cpu deve ter no máximo 15 caracteres"),
  disco: yup
    .string()
    .required("O disco é obrigatório")
    .max(15, "O disco deve ter no máximo 15 caracteres"),
  memoria: yup
    .string()
    .required("A memória é obrigatório")
    .max(15, "A memória deve ter no máximo 15 caracteres"),
  modelo: yup
    .string()
    .required("O modelo é obrigatório")
    .max(15, "O modelo deve ter no máximo 15 caracteres"),
  observacao: yup
    .string()
    .required("A observação é obrigatório")
    .max(15, "A observação deve ter no máximo 15 caracteres"),
  sistema_operacional: yup
    .string()
    .required("O sistema_operacional é obrigatório")
    .max(15, "O sistema_operacional deve ter no máximo 15 caracteres"),
  id_setor: yup.string().required("O setor é obrigatório"),
});

export const UpdateComputador = () => {
  const [computador, setComputador] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateComputadorFormSchema),
  });

  const handleUpdateComputador = async (data) => {
    const newComputador = {
      id: id,
      hostname: data.hostname.trim(),
      cpu: data.cpu.trim(),
      disco: data.disco.trim(),
      memoria: data.memoria.trim(),
      modelo: data.modelo.trim(),
      observacao: data.observacao.trim(),
      sistema_operacional: data.sistema_operacional.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/computador/v1", newComputador, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "Computador atualizado com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/computador"), 1000);
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

  const getComputadorById = async () => {
    try {
      const request = await api.get(`/computador/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setComputador(request.data);
        setValue("hostname", request.data.hostname);
        setValue("cpu", request.data.cpu);
        setValue("disco", request.data.disco);
        setValue("memoria", request.data.memoria);
        setValue("modelo", request.data.modelo);
        setValue("observacao", request.data.observacao);
        setValue("sistema_operacional", request.data.sistema_operacional);
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
    getComputadorById();
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
            Falha ao obter dados do computador
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
          onSubmit={handleSubmit(handleUpdateComputador)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Computador: {computador.hostname}
          </Heading>

          <Divider my="6" borderColor="gray.300" />

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Hostname"
                label="Hostname"
                {...register("hostname")}
                error={formState.errors.hostname}
              />
              <CommonInput
                placeholder="Cpu"
                label="Cpu"
                {...register("cpu")}
                error={formState.errors.cpu}
              />
            </SimpleGrid>
          </VStack>

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Disco"
                label="Disco"
                {...register("disco")}
                error={formState.errors.disco}
              />
              <CommonInput
                placeholder="Memória"
                label="Memória"
                {...register("memoria")}
                error={formState.errors.memoria}
              />
            </SimpleGrid>
          </VStack>

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Modelo"
                label="Modelo"
                {...register("modelo")}
                error={formState.errors.modelo}
              />
              <CommonInput
                placeholder="Observação"
                label="Observação"
                {...register("observacao")}
                error={formState.errors.observacao}
              />
            </SimpleGrid>
          </VStack>

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Sistema Operacional"
                label="Sistema Operacional"
                {...register("sistema_operacional")}
                error={formState.errors.sistema_operacional}
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
                <Button
                  colorScheme="blackAlpha"
                  onClick={() => navigate("/computador")}
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
