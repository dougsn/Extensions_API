import { Button, Icon, useColorMode } from "@chakra-ui/react";
import { RiEditLine } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

export const UpdateButton = ({ endpoint }) => {
  const { colorMode } = useColorMode();
  const navigate = useNavigate();

  return (
    <Button
      size="sm"
      fontSize="sm"
      bgColor={colorMode === "dark" ? "yellow.700" : "yellow.400"}
      _hover={{
        bgColor: colorMode === "dark" ? "yellow.600" : "yellow.300",
      }}
      color="white"
      onClick={() => navigate(`${endpoint}`)}
    >
      <Icon as={RiEditLine} fontSize="20" />
    </Button>
  );
};
