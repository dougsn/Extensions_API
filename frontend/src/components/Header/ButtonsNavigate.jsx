import {
  Flex,
  Box,
  Button,
  Stack,
  useMediaQuery,
  Icon,
} from "@chakra-ui/react";
import { useContext } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";

import { MdEmail } from "react-icons/md";
import { BsFillPeopleFill, BsPrinterFill, BsTelephoneFill } from "react-icons/bs";
import { RiComputerFill, RiOrganizationChart } from "react-icons/ri";

export const ButtonsNavigate = () => {
  const { isAuthenticated, userData } = useContext(AuthenticationContext);

  const location = useLocation();
  const [isLargerThan1001] = useMediaQuery("(min-width: 1001px)");

  const checkUrl = (url, place) => {
    const part = url.split("/");
    if (part[1] === place) {
      return true;
    }

    return false;
  };

  const renderLinksBasedOnPermissions = () => {
    if (
      userData.permissions.some((p) => p.description === "ADMIN") ||
      userData.permissions.some((p) => p.description === "MANAGER")
    ) {
      return (
        <Stack direction="row">
          <Link to={`/setor`}>
            <Box
              active={checkUrl(location.pathname, "setor")}
              bgColor={`${
                checkUrl(location.pathname, "setor") ? "gray.400" : ""
              }`}
              _hover={{ bgColor: "gray.200" }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={RiOrganizationChart} fontSize="20" />
            </Box>
          </Link>
          <Link to={`/email`}>
            <Box
              active={checkUrl(location.pathname, "email")}
              bgColor={`${
                checkUrl(location.pathname, "email") ? "gray.400" : ""
              }`}
              _hover={{ bgColor: "gray.200" }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={MdEmail} fontSize="20" />
            </Box>
          </Link>
          <Link to={`/computador`}>
            <Box
              active={checkUrl(location.pathname, "computador")}
              bgColor={`${
                checkUrl(location.pathname, "computador") ? "gray.400" : ""
              }`}
              _hover={{ bgColor: "gray.200" }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={RiComputerFill} fontSize="20" />
            </Box>
          </Link>
          <Link to={`/impressora`}>
            <Box
              active={checkUrl(location.pathname, "impressora")}
              bgColor={`${
                checkUrl(location.pathname, "impressora") ? "gray.400" : ""
              }`}
              _hover={{ bgColor: "gray.200" }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={BsPrinterFill} fontSize="20" />
            </Box>
          </Link>
          <Link to={`/user`}>
            <Box
              active={checkUrl(location.pathname, "user")}
              bgColor={`${
                checkUrl(location.pathname, "user") ? "gray.400" : ""
              }`}
              _hover={{ bgColor: "gray.200" }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={BsFillPeopleFill} fontSize="20" />
            </Box>
          </Link>
        </Stack>
      );
    }
  };

  return (
    <Flex align="center">
      <Box mr="4" textAlign="right">
        <Box display={"flex"} gap={5}>
          <Stack direction="row">
            {isLargerThan1001 && (
              <Link to={`/ramais`}>
                <Box
                  active={checkUrl(location.pathname, "ramais")}
                  bgColor={`${
                    checkUrl(location.pathname, "ramais") ? "gray.400" : ""
                  }`}
                  _hover={{ bgColor: "gray.200" }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Icon as={BsTelephoneFill} fontSize="20" />
                </Box>
              </Link>
            )}
          </Stack>
          {isAuthenticated && (
            <>{isLargerThan1001 && renderLinksBasedOnPermissions()}</>
          )}
        </Box>
      </Box>
    </Flex>
  );
};
