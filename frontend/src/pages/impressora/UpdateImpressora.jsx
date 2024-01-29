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

const UpdateImpressoraFormSchema = yup.object().shape({
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

export const UpdateImpressora = () => {
  const [impressora, setImpressora] = useState([]);
  const [setor, setSetor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateImpressoraFormSchema),
  });

  const handleUpdateComputador = async (data) => {
    const newImpressora = {
      id: id,
      marca: data.marca.trim(),
      modelo: data.modelo.trim(),
      ip: data.ip.trim(),
      tonner: data.tonner.trim(),
      observacao: data.observacao.trim(),
      id_setor: data.id_setor.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put("/impressora/v1", newImpressora, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.status == 200) {
        toast({
          title: "Impressora atualizada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/impressora"), 1000);
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

  const getImpressoraById = async () => {
    try {
      const request = await api.get(`/impressora/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setImpressora(request.data);
        setValue("marca", request.data.marca);
        setValue("modelo", request.data.modelo);
        setValue("ip", request.data.ip);
        setValue("tonner", request.data.tonner);
        setValue("observacao", request.data.observacao);
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
    getImpressoraById();
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
            Falha ao obter dados da impressora
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
            Editar Impressora: {impressora.modelo}
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
                <Button
                  colorScheme="blackAlpha"
                  onClick={() => navigate("/impressora")}
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
