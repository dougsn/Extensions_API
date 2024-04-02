import { Button, useColorMode } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

export const DeleteButtonPopUp = ({ endpoint }) => {
  const { colorMode } = useColorMode();
  const navigate = useNavigate();

  return (
    <Button
      fontSize={["12px", "16px"]}
      mr={5}
      bgColor={colorMode === "dark" ? "messenger.800" : "messenger.500"}
      color={colorMode === "dark" ? "" : "white"}
      _hover={{
        bgColor: colorMode === "dark" ? "messenger.900" : "messenger.600",
      }}
      onClick={() => navigate(`${endpoint}`)}
    >
      Voltar
    </Button>
  );
};
