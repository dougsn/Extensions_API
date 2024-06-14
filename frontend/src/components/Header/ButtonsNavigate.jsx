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
  useColorMode,
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
import { FaRaspberryPi, FaRunning, FaSatelliteDish, FaWifi } from "react-icons/fa";
import { MdSettingsInputAntenna } from "react-icons/md";
import { ChevronDownIcon } from "@chakra-ui/icons";
import { TiPointOfInterest } from "react-icons/ti";
import { IoHardwareChip } from "react-icons/io5";

export const ButtonsNavigate = () => {
  const { isAuthenticated, userData } = useContext(AuthenticationContext);

  const location = useLocation();
  const [isLargerThan1001] = useMediaQuery("(min-width: 1001px)");
  const { colorMode } = useColorMode();

  const checkUrl = (url, place) => {
    const part = url.split("/");
    if (part[1] === place) {
      return true;
    }

    return false;
  };

  const renderLinksBasedOnPermissions = () => {
    if (userData.permissions.some((p) => p.description === "ADMIN")) {
      return (
        <Stack direction="row">
          <Link to={`/setor`}>
            <Box
              active={checkUrl(location.pathname, "setor")}
              bgColor={`${
                colorMode === "dark" && checkUrl(location.pathname, "setor")
                  ? "gray.700"
                  : colorMode === "light" &&
                    checkUrl(location.pathname, "setor")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
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
                colorMode === "dark" && checkUrl(location.pathname, "email")
                  ? "gray.700"
                  : colorMode === "light" &&
                    checkUrl(location.pathname, "email")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
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
                colorMode === "dark" &&
                checkUrl(location.pathname, "computador")
                  ? "gray.700"
                  : colorMode === "light" &&
                    checkUrl(location.pathname, "computador")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
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
                colorMode === "dark" &&
                checkUrl(location.pathname, "impressora")
                  ? "gray.700"
                  : colorMode === "light" &&
                    checkUrl(location.pathname, "impressora")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
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
                colorMode === "dark" && checkUrl(location.pathname, "user")
                  ? "gray.700"
                  : colorMode === "light" && checkUrl(location.pathname, "user")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={BsFillPeopleFill} fontSize="20" />
            </Box>
          </Link>
          <Link to={`/wifi`}>
            <Box
              active={checkUrl(location.pathname, "wifi")}
              bgColor={`${
                colorMode === "dark" && checkUrl(location.pathname, "wifi")
                  ? "gray.700"
                  : colorMode === "light" && checkUrl(location.pathname, "wifi")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={FaWifi} fontSize="20" />
            </Box>
          </Link>
          <Link to={`/terminal`}>
            <Box
              active={checkUrl(location.pathname, "terminal")}
              bgColor={`${
                colorMode === "dark" && checkUrl(location.pathname, "terminal")
                  ? "gray.700"
                  : colorMode === "light" && checkUrl(location.pathname, "terminal")
                  ? "gray.400"
                  : ""
              }`}
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
              display="flex"
              alignItems="center"
              padding={2}
              borderRadius={"5"}
              transition={"all .5s ease"}
            >
              <Icon as={FaRaspberryPi} fontSize="20" />
            </Box>
          </Link>
          <Menu>
            <MenuButton
              active={
                checkUrl(location.pathname, "local") ||
                checkUrl(location.pathname, "tipo-antena") ||
                checkUrl(location.pathname, "modelo") ||
                checkUrl(location.pathname, "antena")
                  ? colorMode === "dark"
                    ? "gray.700"
                    : "gray.400"
                  : ""
              }
              bgColor={
                checkUrl(location.pathname, "local") ||
                checkUrl(location.pathname, "tipo-antena") ||
                checkUrl(location.pathname, "modelo") ||
                checkUrl(location.pathname, "antena")
                  ? colorMode === "dark"
                    ? "gray.700"
                    : "gray.400"
                  : ""
              }
              px={4}
              py={2}
              transition="all 0.2s"
              borderRadius="md"
              borderWidth="1px"
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
              _expanded={{ bg: "gray.400" }}
              _focus={{ boxShadow: "outline" }}
            >
              <Icon as={FaSatelliteDish} fontSize="20" /> <ChevronDownIcon />
            </MenuButton>
            <MenuList>
              <MenuItem as={Link} to={`/antena`}>
                <Box
                  active={checkUrl(location.pathname, "antena")}
                  bgColor={
                    checkUrl(location.pathname, "antena")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(location.pathname, "antena") &&
                      colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
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
                  bgColor={
                    checkUrl(location.pathname, "tipo-antena")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(location.pathname, "tipo-antena") &&
                      colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
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
                  bgColor={
                    checkUrl(location.pathname, "local")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(location.pathname, "local") &&
                      colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
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
                  bgColor={
                    checkUrl(location.pathname, "modelo")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(location.pathname, "modelo") &&
                      colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
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
          <Menu>
            <MenuButton
              active={
                checkUrl(location.pathname, "catraca") ||
                checkUrl(location.pathname, "manutencao-catraca") ||
                checkUrl(location.pathname, "relatorio-por-dia-e-catraca")
                  ? colorMode === "dark"
                    ? "gray.700"
                    : "gray.400"
                  : ""
              }
              bgColor={
                checkUrl(location.pathname, "catraca") ||
                checkUrl(location.pathname, "manutencao-catraca") ||
                checkUrl(location.pathname, "relatorio-por-dia-e-catraca")
                  ? colorMode === "dark"
                    ? "gray.700"
                    : "gray.400"
                  : ""
              }
              px={4}
              py={2}
              transition="all 0.2s"
              borderRadius="md"
              borderWidth="1px"
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
              }}
              _expanded={{ bg: "gray.400" }}
              _focus={{ boxShadow: "outline" }}
            >
              <Icon as={FaRunning} fontSize="20" /> <ChevronDownIcon />
            </MenuButton>
            <MenuList>
              <MenuItem as={Link} to={`/catraca`}>
                <Box
                  active={checkUrl(location.pathname, "catraca")}
                  bgColor={
                    checkUrl(location.pathname, "catraca")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(location.pathname, "catraca") &&
                      colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={IoHardwareChip} fontSize="20" mr={6} />
                    <Box>Catraca</Box>
                  </Box>
                </Box>
              </MenuItem>
              <MenuItem as={Link} to={`/manutencao-catraca`}>
                <Box
                  active={checkUrl(location.pathname, "manutencao-catraca")}
                  bgColor={
                    checkUrl(location.pathname, "manutencao-catraca")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(location.pathname, "manutencao-catraca") &&
                      colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={IoHardwareChip} fontSize="20" mr={6} />
                    <Box>Manutenção das Catracas</Box>
                  </Box>
                </Box>
              </MenuItem>
              <MenuItem as={Link} to={`/relatorio-por-dia-e-catraca`}>
                <Box
                  active={checkUrl(
                    location.pathname,
                    "relatorio-por-dia-e-catraca"
                  )}
                  bgColor={
                    checkUrl(location.pathname, "relatorio-por-dia-e-catraca")
                      ? colorMode === "dark"
                        ? "gray.400"
                        : ""
                      : ""
                  }
                  _hover={{
                    bgColor:
                      checkUrl(
                        location.pathname,
                        "relatorio-por-dia-e-catraca"
                      ) && colorMode !== "dark"
                        ? "gray.200"
                        : "",
                  }}
                  display="flex"
                  alignItems="center"
                  padding={2}
                  borderRadius={"5"}
                  transition={"all .5s ease"}
                >
                  <Box display="flex" alignItems="center" paddingRight={2}>
                    <Icon as={IoHardwareChip} fontSize="20" mr={6} />
                    <Box>Relatório por Dia e Catraca</Box>
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
                    colorMode === "dark" && checkUrl(location.pathname, "ramal")
                      ? "gray.700"
                      : colorMode === "light" &&
                        checkUrl(location.pathname, "ramal")
                      ? "gray.400"
                      : ""
                  }`}
                  _hover={{
                    bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
                  }}
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
