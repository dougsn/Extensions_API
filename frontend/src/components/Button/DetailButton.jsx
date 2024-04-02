import { Button, Icon, useColorMode } from "@chakra-ui/react";
import { RxMagnifyingGlass } from "react-icons/rx";
import { useNavigate } from "react-router-dom";

export const UpdateButton = ({ endpoint }) => {
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
      <Icon as={RxMagnifyingGlass} fontSize="20" />
    </Button>
  );
};
