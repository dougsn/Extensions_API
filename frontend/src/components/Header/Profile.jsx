import {
  Flex,
  Box,
  Text,
  Button,
  useToast,
  Divider,
  Stack,
  useMediaQuery,
} from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useContext } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { api } from "../../services/api";
import { deleteToken, getToken } from "../../utils/localstorage";

export const Profile = () => {
  const { isAuthenticated, userData, setIsAuthenticated, setUserData } =
    useContext(AuthenticationContext);

  const navigate = useNavigate();
  const location = useLocation();
  const toast = useToast();

  const [isLargerThan850] = useMediaQuery("(max-width: 850px)");

  const logoutUser = () => {
    setIsAuthenticated(false);
    setUserData({});
    deleteToken();
    toast({
      title: "Até a próxima",
      status: "success",
      position: "top-right",
      duration: 1000,
      isClosable: true,
    });
    navigate("/");
  };

  const getUserData = async () => {
    const userToken = getToken();

    if (userToken) {
      try {
        const response = await api.get("/auth/v1", {
          headers: { Authorization: `Bearer ${userToken}` },
        });

        if (response.status == 200) {
          setUserData(response.data);
          setIsAuthenticated(true);
        }
        return true;
      } catch {
        deleteToken();
        setIsAuthenticated(false);
        return false;
      }
    } else {
      deleteToken();
      setIsAuthenticated(false);
      return false;
    }
  };

  const getData = async () => {
    try {
      const response = await getUserData();

      if (response == false) {
        if (isAuthenticated == false && location.pathname != "/") {
          toast({
            title: "Você precisa estar autenticado para usar o sistema.",
            status: "error",
            position: "top-right",
            duration: 3000,
            isClosable: true,
          });
          navigate("/");
        }
      }
    } catch {
      toast({
        title: "erro",
        status: "error",
        position: "top-right",
        duration: 3000,
        isClosable: true,
      });
    }
  };

  useEffect(() => {
    getData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Flex align="center">
      <Box mr="4" textAlign="right">
        <>
          {isAuthenticated && (
            <Box display={"flex"} gap={5}>
              {isLargerThan850 ? (
                <Stack direction="row">
                  <Button
                    fontSize={"small"}
                    onClick={() => logoutUser()}
                    bgColor="blue.500"
                    _hover={{ bgColor: "blue.600" }}
                    color={"white"}
                  >
                    Logout
                  </Button>
                </Stack>
              ) : (
                <Stack direction="row">
                  <Box
                    display={"flex"}
                    flexDir={"column"}
                    alignItems={"center"}
                    justifyContent={"space-around"}
                  >
                    <Text>{userData.name}</Text>
                  </Box>
                  <Divider
                    orientation="vertical"
                    borderColor="gray.300"
                    m="0 10px"
                  />
                  <Button
                    onClick={() => logoutUser()}
                    bgColor="blue.500"
                    _hover={{ bgColor: "blue.600" }}
                    color={"white"}
                  >
                    Logout
                  </Button>
                </Stack>
              )}
            </Box>
          )}
        </>
      </Box>
    </Flex>
  );
};
