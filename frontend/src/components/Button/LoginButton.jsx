import { Button, useColorMode } from "@chakra-ui/react";

export const LoginButton = ({ isLoadingBtn, value }) => {
  const { colorMode } = useColorMode();

  return (
    <Button
      isLoading={isLoadingBtn}
      type="submit"
      p={"0 100px"}
      bgColor={colorMode === "dark" ? "messenger.800" : "messenger.500"}
      color={colorMode === "dark" ? "" : "white"}
      _hover={{
        bgColor: colorMode === "dark" ? "messenger.900" : "messenger.600",
      }}
    >
      {value}
    </Button>
  );
};
