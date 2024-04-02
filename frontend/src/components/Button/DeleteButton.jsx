import { Button, Icon, useColorMode } from "@chakra-ui/react";
import { RiDeleteBinLine } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

export const DeleteButton = ({ endpoint }) => {
  const { colorMode } = useColorMode();
  const navigate = useNavigate();

  return (
    <Button
      size="sm"
      fontSize="sm"
      bgColor={colorMode === "dark" ? "red.800" : "red.500"}
      color={colorMode === "dark" ? "" : "white"}
      _hover={{
        bgColor: colorMode === "dark" ? "red.900" : "red.600",
      }}
      onClick={() => navigate(`${endpoint}`)}
    >
      <Icon as={RiDeleteBinLine} fontSize="20" />
    </Button>
  );
};
