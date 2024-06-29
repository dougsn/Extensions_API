import {
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Text,
  VStack,
  Spinner,
  Flex,
  Alert,
  AlertIcon,
  AlertTitle,
  AlertDescription,
  Badge,
} from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";
import { CommonTextarea } from "../../components/Form/CommonTextarea";
import { VoltarButtonPopUp } from "../../components/Button/VoltarButtonPopUp";
import { useForm } from "react-hook-form";

export const ModalDetailsProjeto = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [projeto, setProjeto] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);

  const onOpen = () => setIsOpen(true);
  const { id } = useParams();
  const navigate = useNavigate();

  const { register, setValue } = useForm({});

  function renderStatus(status) {
    if (status == "Criado") {
      return (
        <Badge variant="solid" colorScheme="teal">
          Criado
        </Badge>
      );
    } else if (status == "Pausado") {
      return (
        <Badge variant="solid" colorScheme="yellow">
          Pausado
        </Badge>
      );
    } else if (status == "Em andamento") {
      return (
        <Badge variant="solid" colorScheme="blue">
          Em andamento
        </Badge>
      );
    } else if (status == "Finalizado") {
      return (
        <Badge variant="solid" colorScheme="green">
          Finalizado
        </Badge>
      );
    }
  }

  const getProjetoById = async () => {
    try {
      const request = await api.get(`/projeto/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.length != 0) {
        setProjeto(request.data);
        setValue("descricao", request.data.descricao);
        setIsLoading(false);
        setErro(false);
        setProjeto(request.data);
      }
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    onOpen();
    getProjetoById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {
        <Modal
          isOpen={isOpen}
          onClose={() => navigate("/projeto")}
          isCentered
          motionPreset="scale"
          size={"2xl"}
        >
          <ModalOverlay
            backdropFilter="auto"
            backdropInvert="30%"
            backdropBlur="6px"
          />
          <ModalContent p={["6", "8"]} m={"5"} fontSize={["14px", "16px"]}>
            {isLoading ? (
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
            ) : erro ? (
              <>
                <Alert
                  status="error"
                  variant="subtle"
                  flexDirection="column"
                  alignItems="center"
                  justifyContent="center"
                  textAlign="center"
                  height="400px"
                  borderRadius={"base"}
                  colorScheme="blue"
                >
                  <AlertIcon boxSize="40px" mr={0} />
                  <AlertTitle mt={4} mb={1} fontSize="xl">
                    Falha ao obter dados do projeto
                  </AlertTitle>
                  <AlertDescription
                    maxWidth="sm"
                    fontSize="lg"
                    fontWeight="500"
                  >
                    Tente novamente mais tarde.
                  </AlertDescription>
                </Alert>
                <Button
                  borderRadius={"base"}
                  colorScheme="blue"
                  onClick={() => navigate("/projeto")}
                >
                  Voltar
                </Button>
              </>
            ) : (
              <>
                <ModalHeader textAlign={"center"}>
                  Detalhes do Projeto: {projeto.nome}
                </ModalHeader>
                <ModalBody textAlign={"center"}>
                  <VStack spacing={5}>
                    <Text>Status: {renderStatus(projeto.nome_status)}</Text>
                    <CommonTextarea
                      label="Descrição"
                      {...register("descricao")}
                    />
                  </VStack>
                </ModalBody>

                <ModalFooter>
                  <VoltarButtonPopUp endpoint={"/projeto"} />
                </ModalFooter>
              </>
            )}
          </ModalContent>
        </Modal>
      }
    </>
  );
};
