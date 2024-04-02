import { Button, useColorMode } from "@chakra-ui/react";

export const CreateButtonWithSubmit = ({ isLoadingBtn }) => {
  const { colorMode } = useColorMode();

  return (
    <Button
      isLoading={isLoadingBtn}
      type="submit"
      bgColor={colorMode === "dark" ? "messenger.800" : "messenger.500"}
      color={colorMode === "dark" ? "" : "white"}
      _hover={{
        bgColor: colorMode === "dark" ? "messenger.900" : "messenger.600",
      }}
    >
      Salvar
    </Button>
  );
};
