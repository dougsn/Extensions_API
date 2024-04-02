import PropTypes from "prop-types";
import {
  Box,
  Text,
  Stack,
  AccordionItem,
  AccordionButton,
  AccordionPanel,
  AccordionIcon,
  useColorMode,
} from "@chakra-ui/react";

export const AccordionSection = ({ title, children }) => {
  const { colorMode } = useColorMode();
  return (
    <AccordionItem border={"none"} mb={"4"}>
      {({ isExpanded }) => (
        <>
          <h2>
            <AccordionButton
              style={{
                borderBottom: isExpanded
                  ? "none"
                  : "2px solid rgba(0, 0, 0, 0.1)",
              }}
              bgColor={
                isExpanded
                  ? colorMode === "dark"
                    ? "gray.900"
                    : "gray.300"
                  : ""
              }
              _hover={{
                bgColor: colorMode === "dark" ? "gray.700" : "gray.200",
              }}
              borderRadius={"5"}
            >
              <Box flex="1" textAlign="left">
                <Text
                  fontWeight="bold"
                  color={isExpanded ? "blue.600" : "blue.400"} // Mude a cor quando estiver selecionado
                  fontSize="small"
                >
                  {title}
                </Text>
              </Box>
              <AccordionIcon />
            </AccordionButton>
          </h2>
          <AccordionPanel p={"4"} m={"2"}>
            <Stack spacing="3" align="stretch">
              {children}
            </Stack>
          </AccordionPanel>
        </>
      )}
    </AccordionItem>
  );
};

AccordionSection.propTypes = {
  title: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};
