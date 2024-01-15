import {
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  useToast,
} from "@chakra-ui/react";
import { useContext, useState } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/api";
import { deleteToken, getToken } from "../../utils/localstorage";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";

export const ConfirmDelete = ({ name, endpoint, id, color }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const { userData, setIsAuthenticated, setUserData } = useContext(
    AuthenticationContext
  );

  const toast = useToast();
  const navigate = useNavigate();

  const onClose = () => setIsOpen(false);
  const onOpen = () => setIsOpen(true);

  const logoutUserDelete = () => {
    setIsAuthenticated(false);
    setUserData({});
    deleteToken();
    toast({
      title: "Usuário deletado com sucesso!",
      status: "success",
      position: "top-right",
      duration: 1000,
      isClosable: true,
    });
    setTimeout(() => navigate("/"), 1000);
  };

  async function handleRemove() {
    try {
      setIsLoading(true);

      const response = await api.delete(`${endpoint}/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      if (response.status == 204 && endpoint == "user" && userData.id == id) {
        logoutUserDelete();
        setTimeout(() => navigate("/"), 1000);
      } else if (response.status == 204) {
        toast({
          title: `${name} removido(a) com sucesso!`,
          status: "success",
          position: "top-right",
          duration: 3000,
          isClosable: true,
        });

        setTimeout(() => {
          setIsLoading(false);
          navigate(`/${endpoint}`);
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
        title: error.response.data.error,
        status: "error",
        position: "top-right",
        duration: 3000,
        isClosable: true,
      });
    }
  }

  return (
    <>
      {/* Botão para abrir o modal */}
      <Button
        p={5}
        colorScheme={color}
        size="sm"
        fontSize={["12px", "16px"]}
        onClick={onOpen}
      >
        Deletar
      </Button>

      {/* Modal */}
      <Modal
        isOpen={isOpen}
        onClose={() => navigate(`/${endpoint}`)}
        isCentered
        motionPreset="scale"
      >
        <ModalOverlay
          backdropFilter="auto"
          backdropInvert="30%"
          backdropBlur="6px"
        />
        <ModalContent p={["6", "8"]} m={"5"} fontSize={["14px", "16px"]}>
          <ModalHeader textAlign={"center"} fontSize={["14px", "20px"]} t>
            Tem certeza que deseja excluir?
          </ModalHeader>
          <ModalFooter>
            {/* Botões do rodapé do modal, se necessário */}
            <Button
              fontSize={["12px", "16px"]}
              mr={5}
              colorScheme="blue"
              onClick={onClose}
            >
              Não
            </Button>
            <Button
              fontSize={["12px", "16px"]}
              colorScheme="red"
              onClick={handleRemove}
              isLoading={isLoading}
            >
              Sim
            </Button>
            {/* Adicione mais botões conforme necessário */}
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

ConfirmDelete.propTypes = {
  name: PropTypes.string,
  endpoint: PropTypes.string,
  id: PropTypes.string.isRequired,
  color: PropTypes.string.isRequired,
};
