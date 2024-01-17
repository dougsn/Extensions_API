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
} from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";

export const ModalDetailsUsuario = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [usuario, setUsuario] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erro, setErro] = useState(false);

  const onOpen = () => setIsOpen(true);
  const { id } = useParams();
  const navigate = useNavigate();

  const getUserById = async () => {
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
    getUserById();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {
        <Modal
          isOpen={isOpen}
          onClose={() => {
            usuario.permissions.some((p) => p.description === "ADMIN") ||
            usuario.permissions.some((p) => p.description === "MANAGER")
              ? navigate("/user")
              : navigate("/ramais");
          }}
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
                    Falha ao obter dados do usuário
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
                  onClick={() => navigate("/tanque")}
                >
                  Voltar
                </Button>
              </>
            ) : (
              <>
                <ModalHeader textAlign={"center"}>
                  Detalhes do Usuário: {usuario.login}
                </ModalHeader>
                <ModalBody textAlign={"center"}>
                  <VStack spacing={5}>
                    <Text>Login: {usuario.name}</Text>
                  </VStack>
                </ModalBody>

                <ModalFooter>
                  <Button
                    fontSize={["12px", "16px"]}
                    colorScheme="blue"
                    onClick={() => {
                      usuario.permissions.some(
                        (p) => p.description === "ADMIN"
                      ) ||
                      usuario.permissions.some(
                        (p) => p.description === "MANAGER"
                      )
                        ? navigate("/user")
                        : navigate("/ramais");
                    }}
                  >
                    Voltar
                  </Button>
                </ModalFooter>
              </>
            )}
          </ModalContent>
        </Modal>
      }
    </>
  );
};
