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
  useColorMode,
} from "@chakra-ui/react";

import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ConfirmDelete } from "../../components/ConfirmDelete";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";
import { DeleteButtonPopUp } from "../../components/Button/DeleteButtonPopUp";

export const DeleteUsuario = () => {
  const [usuario, setUsuario] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);

  const [isOpen, setIsOpen] = useState(false);
  const onOpen = () => setIsOpen(true);

  const { colorMode } = useColorMode();
  const { id } = useParams();
  const navigate = useNavigate();

  const getUsuarioById = async () => {
    try {
      const request = await api.get(`/user/v1/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (request.length != 0) {
        setIsLoading(false);
        setErro(false);
        setUsuario(request.data);
      }
    } catch (error) {
      setErro(true);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    onOpen();
    getUsuarioById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {
        <Modal
          isOpen={isOpen}
          onClose={() => navigate("/user")}
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
                  Falha ao obter dados do usuário
                </AlertTitle>
                <AlertDescription maxWidth="sm" fontSize="lg" fontWeight="500">
                  Tente novamente mais tarde.
                </AlertDescription>
              </Alert>
            ) : (
              <>
                <ModalHeader textAlign={"center"}>
                  Deletar Usuário: {usuario.name}
                </ModalHeader>
                <ModalBody textAlign={"center"}>
                  <VStack spacing={5}>
                    <Text>Login: {usuario.name}</Text>
                  </VStack>
                </ModalBody>

                <ModalFooter>
                  <DeleteButtonPopUp endpoint={"/user"} />

                  <ConfirmDelete
                    color="red"
                    id={id}
                    name={"Usuário"}
                    endpoint={"user"}
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
