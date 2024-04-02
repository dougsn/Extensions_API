import {
  Box,
  Button,
  Divider,
  Drawer,
  DrawerBody,
  DrawerCloseButton,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  Icon,
  Text,
  useColorMode,
  useDisclosure,
  useMediaQuery,
} from "@chakra-ui/react";
import { useContext } from "react";
import { AiOutlineMenu } from "react-icons/ai";
import { RiHomeLine } from "react-icons/ri";
import { Link } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { AccordionNav } from "../Sidebar/AccordionNav";
import { NavLink } from "../Sidebar/NavLink";
import { BsTelephoneFill } from "react-icons/bs";

export const Menu = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [isLargerThan850] = useMediaQuery("(max-width: 850px)");
  const { colorMode } = useColorMode();

  const { isAuthenticated, userData } = useContext(AuthenticationContext);

  const checkUrl = (url, place) => {
    const part = url.split("/");
    if (part[1] === place) {
      return true;
    }

    return false;
  };

  return (
    <>
      <Button
        bgColor={colorMode === "dark" ? "blackAlpha.300" : "white"}
        color={colorMode === "dark" ? "" : "white"}
        _hover={{
          bgColor: colorMode === "dark" ? "blackAlpha.500" : "blackAlpha.100",
        }}
        onClick={onOpen}
        marginRight={5}
        transition={"all .5s ease"}
      >
        <Icon
          as={AiOutlineMenu}
          color={colorMode === "dark" ? "white" : "black"}
        />
      </Button>
      <Drawer isOpen={isOpen} placement="left" onClose={onClose}>
        <DrawerOverlay />
        <DrawerContent>
          <DrawerCloseButton />
          <DrawerHeader>Navegação</DrawerHeader>

          <DrawerBody>
            {isAuthenticated ? (
              <AccordionNav close={onClose} />
            ) : (
              <Link to={"/ramal"} onClick={onClose}>
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
                  <Text fontWeight="medium" ml="4">
                    Ramais
                  </Text>
                </Box>
              </Link>
            )}
          </DrawerBody>
          {isAuthenticated && (
            <DrawerFooter>
              {isLargerThan850 && (
                <>
                  <Box
                    display={"flex"}
                    flexDir={"column"}
                    alignItems={"center"}
                    justifyContent={"space-around"}
                  >
                    <Text>{userData.name}</Text>
                  </Box>
                </>
              )}
            </DrawerFooter>
          )}
        </DrawerContent>
      </Drawer>
    </>
  );
};
