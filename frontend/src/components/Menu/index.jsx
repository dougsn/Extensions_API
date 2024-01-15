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

export const Menu = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [isLargerThan850] = useMediaQuery("(max-width: 850px)");

  const { isAuthenticated, userData } = useContext(AuthenticationContext);

  return (
    <>
      <Button
        color="blackAlpha.700"
        onClick={onOpen}
        marginRight={5}
        bgColor={"gray.50"}
        transition={"all .5s ease"}
      >
        <Icon as={AiOutlineMenu} />
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
              <Link to={""} onClick={onClose}>
                <NavLink as="a" icon={RiHomeLine}>
                  Login
                </NavLink>
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
