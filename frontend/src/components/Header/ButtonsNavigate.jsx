import { Flex, Box, Button, Stack, useMediaQuery } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useContext } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
export const ButtonsNavigate = () => {
  const { isAuthenticated } = useContext(AuthenticationContext);

  const [isLargerThan1001] = useMediaQuery("(min-width: 1001px)");

  return (
    <Flex align="center">
      <Box mr="4" textAlign="right">
        <>
          {isAuthenticated && (
            <>
              {isLargerThan1001 && (
                <Box display={"flex"} gap={5}>
                  {
                    <Stack direction="row">
                      <Button
                        bgColor="blue.500"
                        _hover={{ bgColor: "blue.600" }}
                        color={"white"}
                      >
                        Ramais
                      </Button>
                    </Stack>
                  }
                </Box>
              )}
            </>
          )}
        </>
      </Box>
    </Flex>
  );
};
