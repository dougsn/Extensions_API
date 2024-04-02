import { Button, useColorMode } from "@chakra-ui/react";

export function PaginationItem({ isCurrent = false, number, onPageChange }) {
  const { colorMode } = useColorMode();
  if (isCurrent) {
    return (
      <Button
        size="sm"
        fontSize="xs"
        width="4"
        bgColor={colorMode === "dark" ? "messenger.800" : "messenger.500"}
        color={colorMode === "dark" ? "" : "white"}
        _hover={{
          bgColor: colorMode === "dark" ? "messenger.900" : "messenger.600",
        }}
        disabled
        _disabled={{
          bg: "blue.500",
          cursor: "default",
        }}
      >
        {number + 1}
      </Button>
    );
  }

  return (
    <Button
      size="sm"
      fontSize="xs"
      width="4"
      colorScheme="blue"
      bg="blue.900"
      _hover={{
        bg: "blue.500",
      }}
      onClick={() => onPageChange(number)}
    >
      {number + 1}
    </Button>
  );
}
