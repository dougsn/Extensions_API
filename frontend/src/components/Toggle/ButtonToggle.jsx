import { MoonIcon, SunIcon } from "@chakra-ui/icons";
import { Button, useColorMode } from "@chakra-ui/react";

export function ButtonToggle() {
  const { colorMode, toggleColorMode } = useColorMode();
  return (
    <Button
      bgColor={"transparent"}
      _hover={{
        bgColor:
          colorMode === "dark" ? "gray.700" : "gray.300",
      }}
      transition={"all .5s ease"}
      onClick={toggleColorMode}
    >
      {colorMode === "light" ? <MoonIcon /> : <SunIcon />}
    </Button>
  );
}
