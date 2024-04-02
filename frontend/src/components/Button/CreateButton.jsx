import { Button, Icon, useColorMode } from "@chakra-ui/react";
import { RiAddLine } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

export const CreateButton = ({ endpoint }) => {
  const { colorMode } = useColorMode();
  const navigate = useNavigate();

  return (
    <Button
      size="sm"
      fontSize="sm"
      bgColor={colorMode === "dark" ? "messenger.800" : "messenger.500"}
      color={colorMode === "dark" ? "" : "white"}
      _hover={{
        bgColor: colorMode === "dark" ? "messenger.900" : "messenger.600",
      }}
      onClick={() => navigate(`${endpoint}`)}
    >
      <Icon as={RiAddLine} fontSize="20" />
    </Button>
  );
};
