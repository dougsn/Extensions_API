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
import { CommonInputData } from "../../components/Form/CommonInputData";
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";

const UpdateManutencaoCatracaFormSchema = yup.object().shape({
  dia: yup.string().required("O dia é obrigatório"),
  defeito: yup.string().required("O defeito é obrigatório"),
  observacao: yup.string().required("A observação é obrigatória"),
  id_catraca: yup.string().required("A catraca é obrigatória"),
});

export const UpdateManutencaoCatraca = () => {
  const [manutencaoCatraca, setManutencaoCatraca] = useState([]);
  const [dia, setDia] = useState("");
  const [catraca, setCatraca] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isLoadingBtn, setIsLoadingBtn] = useState(false);
  const [erro, setErro] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();
  const toast = useToast();

  const { register, handleSubmit, formState, setValue } = useForm({
    resolver: yupResolver(UpdateManutencaoCatracaFormSchema),
  });

  const handleInputData = (dia) => {
    setDia(dia);
  };

  const getCatraca = async () => {
    try {
      const request = await api.get(`/catraca/v1/all`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      setCatraca(request.data);
    } catch (error) {
      return null;
    }
  };

  const handleUpdateManutencaoCatraca = async (data) => {
    const newManutencaoCatraca = {
      id: id,
      dia: data.dia.trim(),
      defeito: data.defeito.trim(),
      observacao: data.observacao.trim(),
      id_catraca: data.id_catraca.trim(),
    };
    setIsLoadingBtn(true);
    try {
      const request = await api.put(
        "/manutencao-catraca/v1",
        newManutencaoCatraca,
        {
          headers: { Authorization: `Bearer ${getToken()}` },
        }
      );
      if (request.status == 200) {
        toast({
          title: "Manutenção atualizada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => navigate("/manutencao-catraca"), 1000);
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

  const getManutencaoCatracaById = async () => {
    try {
      const request = await api.get(`/manutencao-catraca/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });

      if (request.length != 0) {
        setErro(false);
        setManutencaoCatraca(request.data);
        setValue("dia", request.data.dia);
        setValue("defeito", request.data.defeito);
        setValue("observacao", request.data.observacao);
        setValue("id_catraca", request.data.id_catraca);
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
    getCatraca();
    getManutencaoCatracaById();
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
            Falha ao obter dados da manutenção
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
          onSubmit={handleSubmit(handleUpdateManutencaoCatraca)}
        >
          <Heading size="lg" fontWeight="500">
            Editar Catraca: {catraca.nomeCatraca}
          </Heading>

          <Divider my="6" borderColor="gray.300" />

          <VStack spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInputData
                handleChange={handleInputData}
                placeholder={dia}
                label="Dia da Manutenção"
                {...register("dia")}
                error={formState.errors.dia}
              />
              <CommonInput
                placeholder="Defeito"
                label="Defeito"
                {...register("defeito")}
                error={formState.errors.defeito}
              />
            </SimpleGrid>
          </VStack>

          <VStack pt={5} spacing="8">
            <SimpleGrid minChildWidth="240px" spacing={["6", "8"]} w="100%">
              <CommonInput
                placeholder="Procedimento"
                label="Procedimento"
                {...register("observacao")}
                error={formState.errors.observacao}
              />

              <CommonSelect
                entity={catraca}
                placeholder="Selecione umaa catraca"
                label={"Catraca"}
                {...register("id_catraca")}
                error={formState.errors.id_catraca}
              />
            </SimpleGrid>
          </VStack>
          <Flex mt="8" justify="flex-end">
            <HStack spacing="4">
              <Box>
                <VoltarButtonPopUp endpoint={"/manutencao-catraca"} />
              </Box>
              <CreateButtonWithSubmit isLoadingBtn={isLoadingBtn} />
            </HStack>
          </Flex>
        </Box>
      )}
    </>
  );
};
