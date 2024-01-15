import {
  Box,
  Drawer,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  DrawerHeader,
  DrawerBody,
  useMediaQuery,
} from "@chakra-ui/react";
import { useContext } from "react";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { AccordionNav } from "./AccordionNav";

export const Sidebar = () => {
  const [isLargerThan1280] = useMediaQuery("(max-width: 1280px)");
  const { isAuthenticated } =
    useContext(AuthenticationContext);

  return (
    <>
      {isLargerThan1280 ? (
        <Drawer>
          <DrawerOverlay>
            <DrawerContent p="4">
              <DrawerCloseButton mt="6" />
              <DrawerHeader>Navegação</DrawerHeader>

              <DrawerBody>
                <AccordionNav />
              </DrawerBody>
            </DrawerContent>
          </DrawerOverlay>
        </Drawer>
      ) : (
        <>
          {isAuthenticated && (
            <Box as="aside" w="64" mr="8">
              <Box>
                <AccordionNav />
              </Box>
            </Box>
          )}
        </>
      )}
    </>
  );
};
