import {
  Flex,
  Box,
  Button,
  Stack,
  useMediaQuery,
  Icon,
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  MenuDivider,
} from "@chakra-ui/react";
import { useContext } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";

import { MdEmail, MdPlace } from "react-icons/md";
import {
  BsFillPeopleFill,
  BsPrinterFill,
  BsTelephoneFill,
} from "react-icons/bs";
import { RiComputerFill, RiOrganizationChart } from "react-icons/ri";
import { FaSatelliteDish } from "react-icons/fa";
import { MdSettingsInputAntenna } from "react-icons/md";
import { ChevronDownIcon } from "@chakra-ui/icons";
import { TiPointOfInterest } from "react-icons/ti";

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

          <Menu>
            <MenuButton
              active={checkUrl(
                location.pathname,
                "local" || checkUrl(location.pathname, "tipo-antena")
                  ? "gray.400"
                  : "" || checkUrl(location.pathname, "modelo")
                  ? "gray.400"
                  : "" || checkUrl(location.pathname, "antena")
                  ? "gray.400"
                  : ""
              )}
              bgColor={`${
                checkUrl(location.pathname, "local")
                  ? "gray.400"
                  : "" || checkUrl(location.pathname, "tipo-antena")
                  ? "gray.400"
                  : "" || checkUrl(location.pathname, "modelo")
                  ? "gray.400"
                  : "" || checkUrl(location.pathname, "antena")
                  ? "gray.400"
                  : ""
              }`}
              px={4}
              py={2}
              transition="all 0.2s"
              borderRadius="md"
              borderWidth="1px"
              _hover={{ bg: "gray.200" }}
              _expanded={{ bg: "gray.400" }}
              _focus={{ boxShadow: "outline" }}
            >
              <Icon as={FaSatelliteDish} fontSize="20" /> <ChevronDownIcon />
            </MenuButton>
            <MenuList>
            <MenuItem as={Link} to={`/antena`}>
                <Box
                  active={checkUrl(location.pathname, "antena")}
                  bgColor={`${
                    checkUrl(location.pathname, "antena") ? "gray.400" : ""
                  }`}
                  _hover={{ bgColor: "gray.200" }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={FaSatelliteDish} fontSize="20" mr={6} />
                    <Box>Antena</Box>
                  </Box>
                </Box>
              </MenuItem>
              <MenuItem as={Link} to={`/tipo-antena`}>
                <Box
                  active={checkUrl(location.pathname, "tipo-antena")}
                  bgColor={`${
                    checkUrl(location.pathname, "tipo-antena") ? "gray.400" : ""
                  }`}
                  _hover={{ bgColor: "gray.200" }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={MdSettingsInputAntenna} fontSize="20" mr={6} />
                    <Box>Tipo de Antena</Box>
                  </Box>
                </Box>
              </MenuItem>
              <MenuItem as={Link} to={`/local`}>
                <Box
                  active={checkUrl(location.pathname, "local")}
                  bgColor={`${
                    checkUrl(location.pathname, "local") ? "gray.400" : ""
                  }`}
                  _hover={{ bgColor: "gray.200" }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={MdPlace} fontSize="20" mr={6} />
                    <Box>Local</Box>
                  </Box>
                </Box>
              </MenuItem>
              <MenuItem as={Link} to={`/modelo`}>
                <Box
                  active={checkUrl(location.pathname, "modelo")}
                  bgColor={`${
                    checkUrl(location.pathname, "modelo") ? "gray.400" : ""
                  }`}
                  _hover={{ bgColor: "gray.200" }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={TiPointOfInterest} fontSize="20" mr={6} />
                    <Box>Modelo</Box>
                  </Box>
                </Box>
              </MenuItem>
            </MenuList>
          </Menu>
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
              <Link to={`/ramal`}>
                <Box
                  active={checkUrl(location.pathname, "ramal")}
                  bgColor={`${
                    checkUrl(location.pathname, "ramal") ? "gray.400" : ""
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
