import { Box, Flex, Heading } from "@chakra-ui/react";

export const Home = () => {
  return (
    <Box flex="1" p="8">
      <Flex mb="8" justify="center" align="center">
        <Heading size="lg" fontWeight="500">
          Bem vindo ao SGI
        </Heading>
      </Flex>
    </Box>
  );
};
