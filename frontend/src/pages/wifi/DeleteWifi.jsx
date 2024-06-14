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

export const DeleteWifi = () => {
  const [wifi, setWifi] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);

  const [isOpen, setIsOpen] = useState(false);
  const onOpen = () => setIsOpen(true);

  const { id } = useParams();
  const navigate = useNavigate();

  const getWifiById = async () => {
    try {
      const request = await api.get(`/wifi/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.length != 0) {
        setIsLoading(false);
        setErro(false);
        setWifi(request.data);
      }
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    onOpen();
    getWifiById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {
        <Modal
          isOpen={isOpen}
          onClose={() => navigate("/wifi")}
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
                  Falha ao obter dados do WI-FI
                </AlertTitle>
                <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
                  Tente novamente mais tarde.
                </AlertDescription>
              </Alert>
            ) : (
              <>
                <ModalHeader textAlign={"center"}>
                  Deletar WI-FI do: {wifi.ssid}
                </ModalHeader>
                <ModalBody textAlign={"center"}>
                  <VStack spacing={5}>
                    <Text>Usuário: {wifi.usuario}</Text>
                    <Text>IP: {wifi.ip}</Text>
                    <Text>Senha do Browser: {wifi.senha_browser}</Text>
                    <Text>Senha do WIFI: {wifi.senha_wifi}</Text>
                    <Text>Setor: {wifi.nome_setor}</Text>
                  </VStack>
                </ModalBody>

                <ModalFooter>
                  <DeleteButtonPopUp endpoint={"/wifi"} />

                  <ConfirmDelete
                    color="red"
                    id={id}
                    name={"WI-FI"}
                    endpoint={"wifi"}
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