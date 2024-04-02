import PropTypes from "prop-types";
import { Box, Icon, Text, useColorMode } from "@chakra-ui/react";

export const NavLink = ({ icon, children, active }) => {
  const { colorMode } = useColorMode();
  return (
    <Box
      display="flex"
      alignItems="center"
      padding={2}
      bgColor={`${
        active ? (colorMode === "dark" ? "gray.900" : "gray.300") : ""
      }`}
      borderRadius={"5"}
      transition={"all .5s ease"}
      _hover={{
        backgroundColor: colorMode === "dark" ? "gray.700" : "gray.100",
      }}
    >
      <Icon as={icon} fontSize="20" />
      <Text fontWeight="medium" ml="4">
        {children}
      </Text>
    </Box>
  );
};

NavLink.propTypes = {
  icon: PropTypes.func.isRequired,
  children: PropTypes.node.isRequired,
  active: PropTypes.bool,
};
