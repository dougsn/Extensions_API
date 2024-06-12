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
import { CommonInputData } from "../../components/Form/CommonInputData";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";
import { useEffect, useState } from "react";
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { CreateButtonWithSubmit } from "../../components/Button/CreateButtonWithSubmit";
import { CommonSelect } from "../../components/Form/CommonSelect";

const CreateManutencaoCatracaFormSchema = yup.object().shape({
  dia: yup.string().required("O dia é obrigatório"),
  defeito: yup.string().required("O defeito é obrigatório"),
  observacao: yup.string().required("A observação é obrigatória"),
  id_catraca: yup.string().required("A catraca é obrigatória"),
});

export const CreateManutencaoCatraca = () => {
  const toast = useToast();
  const navigate = useNavigate();

  const [catraca, setCatraca] = useState([]);
  const [dia, setDia] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(CreateManutencaoCatracaFormSchema),
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

  const handleCreateManutencaoCatracaFormSchema = async (data) => {
    const newManutencaoCatraca = {
      dia: data.dia.trim(),
      defeito: data.defeito.trim(),
      observacao: data.observacao.trim(),
      id_catraca: data.id_catraca.trim(),
    };
    setIsLoading(true);

    try {
      const request = await api.post(
        "/manutencao-catraca/v1",
        newManutencaoCatraca,
        {
          headers: { Authorization: `Bearer ${getToken()}` },
        }
      );
      if (request.status == 201) {
        toast({
          title: "Manutenção criada com sucesso!",
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });
        setTimeout(() => {
          navigate(`/manutencao-catraca`);
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
    getCatraca();
  }, []);

  return (
    <Box
      as="form"
      flex="1"
      borderRadius={8}
      p={["6", "8"]}
      onSubmit={handleSubmit(handleCreateManutencaoCatracaFormSchema)}
    >
      <Heading size="lg" fontWeight="500">
        Criar Manutenção
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
            placeholder="Observação"
            label="Observação"
            {...register("observacao")}
            error={formState.errors.observacao}
          />

          <CommonSelect
            entity={catraca}
            placeholder="Selecione uma catraca"
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
          <CreateButtonWithSubmit isLoadingBtn={isLoading} />
        </HStack>
      </Flex>
    </Box>
  );
};
