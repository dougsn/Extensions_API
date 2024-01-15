import PropTypes from "prop-types";
import { Box, Icon, Text } from "@chakra-ui/react";

// { backgroundColor: "gray.200", borderRadius: "5", transition: "background-color .3s ease, border-radius .3s ease" }

export const NavLink = ({ icon, children, active }) => {
  return (
    <Box
      display="flex"
      alignItems="center"
      padding={2}
      bgColor={`${active ? "gray.300" : ""}`}
      borderRadius={"5"}
      transition={"all .5s ease"}
      _hover={{backgroundColor: "gray.100"}}
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
