import {
  Flex,
  Box,
  Text,
  Button,
  useToast,
  Divider,
  Stack,
  useMediaQuery,
  Icon,
} from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useContext } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { api } from "../../services/api";
import { deleteToken, getToken } from "../../utils/localstorage";
import { IoLogOut } from "react-icons/io5";
import { ButtonToggle } from "../Toggle/ButtonToggle";

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
        if (isAuthenticated == false && location.pathname != "/ramal" && location.pathname != "/" && location.pathname != "") {
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
                  <Box
                    as="button"
                    onClick={() => logoutUser()}
                    _hover={{ bgColor: "gray.200" }}
                    display="flex"
                    alignItems="center"
                    padding={2}
                    borderRadius={"5"}
                    transition={"all .5s ease"}
                  >
                    <Icon as={IoLogOut} fontSize="25" />
                  </Box>
                  <Divider
                    orientation="vertical"
                    borderColor="gray.300"
                    m="0 10px"
                  />
                  <ButtonToggle />
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
                  <Box
                    as="button"
                    onClick={() => logoutUser()}
                    _hover={{ bgColor: "gray.200" }}
                    display="flex"
                    alignItems="center"
                    padding={2}
                    borderRadius={"5"}
                    transition={"all .5s ease"}
                  >
                    <Icon as={IoLogOut} fontSize="25" />
                  </Box>
                  <Divider
                    orientation="vertical"
                    borderColor="gray.300"
                    m="0 10px"
                  />
                  <ButtonToggle />
                </Stack>
              )}
            </Box>
          )}
        </>
      </Box>
    </Flex>
  );
};
