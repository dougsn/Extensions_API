import { Button } from "@chakra-ui/react";


export function PaginationItem({
  isCurrent = false,
  number,
  onPageChange
}) {
  if (isCurrent) {
    return (
      <Button
        size="sm"
        fontSize="xs"
        width="4"
        colorScheme="blue"
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
