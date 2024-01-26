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
  Box,
  Spinner,
  Flex,
  Alert,
  AlertIcon,
  AlertTitle,
  AlertDescription,
} from "@chakra-ui/react";

import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ConfirmDelete } from "../../components/ConfirmDelete";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";

export const DeleteComputador = () => {
  const [computador, setComputador] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);

  const [isOpen, setIsOpen] = useState(false);
  const onOpen = () => setIsOpen(true);

  const { id } = useParams();
  const navigate = useNavigate();

  const getComputadorlById = async () => {
    try {
      const request = await api.get(`/computador/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.length != 0) {
        setIsLoading(false);
        setErro(false);
        setComputador(request.data);
      }
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    onOpen();
    getComputadorlById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {
        <Modal
          isOpen={isOpen}
          onClose={() => navigate("/computador")}
          isCentered
          motionPreset="scale"
        >
          <ModalOverlay
            backdropFilter="auto"
            backdropInvert="30%"
            backdropBlur="6px"
          />
          <ModalContent p={["6", "8"]} m={"5"} fontSize={["14px", "16px"]}>
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
                  Falha ao obter dados dos computadores
                </AlertTitle>
                <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
                  Tente novamente mais tarde.
                </AlertDescription>
              </Alert>
            ) : (
              <>
                <ModalHeader textAlign={"center"}>
                  Deletar Computador: {computador.hostname}
                </ModalHeader>
                <ModalBody textAlign={"center"}>
                  <VStack spacing={5}>
                    <Text>Hostname: {computador.hostname}</Text>
                    <Text>Cpu: {computador.cpu}</Text>
                    <Text>Memória: {computador.memoria}</Text>
                    <Text>Disco: {computador.disco}</Text>
                    <Text>Modelo: {computador.modelo}</Text>
                    <Text>Observação: {computador.observacao}</Text>
                    <Text>Sistema Operacional: {computador.sistema_operacional}</Text>
                    <Text>Setor: {computador.nome_setor}</Text>
                  </VStack>
                </ModalBody>

                <ModalFooter>
                  <Button
                    fontSize={["12px", "16px"]}
                    mr={5}
                    colorScheme="blue"
                    onClick={() => navigate("/computador")}
                  >
                    Voltar
                  </Button>

                  <ConfirmDelete
                    color="red"
                    id={id}
                    name={"Computador"}
                    endpoint={"computador"}
                  ></ConfirmDelete>
                </ModalFooter>
              </>
            )}
          </ModalContent>
        </Modal>
      }
    </>
  );
};
