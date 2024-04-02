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
import { DeleteButtonPopUp } from "../../components/Button/DeleteButtonPopUp";

export const DeleteAntena = () => {
  const [antena, setAntena] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);

  const [isOpen, setIsOpen] = useState(false);
  const onOpen = () => setIsOpen(true);

  const { id } = useParams();
  const navigate = useNavigate();

  const getAntenaById = async () => {
    try {
      const request = await api.get(`/antena/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.length != 0) {
        setIsLoading(false);
        setErro(false);
        setAntena(request.data);
      }
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    onOpen();
    getAntenaById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {
        <Modal
          isOpen={isOpen}
          onClose={() => navigate("/antena")}
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
                  Falha ao obter dados das antenas
                </AlertTitle>
                <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
                  Tente novamente mais tarde.
                </AlertDescription>
              </Alert>
            ) : (
              <>
                <ModalHeader textAlign={"center"}>
                  Deletar Antena: {antena.ssid}
                </ModalHeader>
                <ModalBody textAlign={"center"}>
                  <VStack spacing={5}>
                    <Text>IP: {antena.ip}</Text>
                    <Text>SSID: {antena.ssid}</Text>
                    <Text>Localização: {antena.localizacao}</Text>
                    <Text>Senha: {antena.senha}</Text>
                    <Text>Local: {antena.nome_local}</Text>
                    <Text>Modelo: {antena.nome_modelo}</Text>
                    <Text>Tipo de Antena: {antena.nome_tipo_antena}</Text>
                  </VStack>
                </ModalBody>

                <ModalFooter>
                  <DeleteButtonPopUp endpoint={"/antena"} />

                  <ConfirmDelete
                    color="red"
                    id={id}
                    name={"Antena"}
                    endpoint={"antena"}
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
