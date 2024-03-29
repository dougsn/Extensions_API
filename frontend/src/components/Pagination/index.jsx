import { Box, Stack, Text } from "@chakra-ui/react";
import { PaginationItem } from "./PaginationItem";

const siblingsCount = 1;

function generatePagesArray(from, to) {
  return [...new Array(to - from)]
    .map((_, index) => {
      return from + index + 1;
    })
    .filter((page) => page > 0);
}

export function Pagination({
  lastPages,
  currentPage = 0,
  onPageChange,
  size = 0,
  totalElements = 0,
}) {
  const lastPage = lastPages - 1;

  const previousPages =
    currentPage > 1
      ? generatePagesArray(currentPage - 1 - siblingsCount, currentPage - 1)
      : [];

  const nextPages =
    currentPage < lastPage
      ? generatePagesArray(
          currentPage,
          Math.min(currentPage + siblingsCount, lastPage)
        )
      : [];

  const startItem = currentPage * size + 1;
  const endItem = Math.min((currentPage + 1) * size, totalElements);

  return (
    <Stack
      direction={["column", "row"]}
      spacing="6"
      mt="8"
      justify="flex-end"
      align="center"
      justifyContent={"space-between"}
    >
      <Box>
        <strong>{startItem}</strong> - <strong>{endItem}</strong> de{" "}
        <strong>{totalElements}</strong>
      </Box>
      <Stack direction="row" spacing="2">
        {currentPage > 1 - siblingsCount && (
          <>
            <PaginationItem onPageChange={onPageChange} number={0} />
            {currentPage > 1 + siblingsCount && (
              <Text color="messenger.900" width="8" textAlign="center">
                ...
              </Text>
            )}
          </>
        )}

        {previousPages.length > 0 &&
          previousPages.map((page) => {
            return (
              <PaginationItem
                onPageChange={onPageChange}
                key={page}
                number={page}
              />
            );
          })}

        <PaginationItem
          onPageChange={onPageChange}
          number={currentPage}
          isCurrent
        />

        {nextPages.length > 0 &&
          nextPages.map((page) => {
            return (
              <PaginationItem
                onPageChange={onPageChange}
                key={page}
                number={page}
              />
            );
          })}

        {currentPage + siblingsCount < lastPage && (
          <>
            {currentPage + 1 + siblingsCount && (
              <Text color="messenger.900" width="8" textAlign="center">
                ...
              </Text>
            )}
            <PaginationItem onPageChange={onPageChange} number={lastPage} />
          </>
        )}
      </Stack>
    </Stack>
  );
}
