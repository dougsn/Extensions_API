import { Button, Flex, Stack, useMediaQuery } from "@chakra-ui/react";
import { Profile } from "../Header/Profile";
import { Logo } from "../Header/Logo";
import { Menu } from "../Menu";
import { ButtonsNavigate } from "./ButtonsNavigate";

export const Header = () => {
  const [isLargerThan1000] = useMediaQuery("(max-width: 1000px)");

  return (
    <Flex
      as="header"
      w="100%"
      h="20"
      mx="auto"
      px="6"
      boxShadow="lg"
      align="center"
    >
      {isLargerThan1000 && <Menu />}
      <Logo />
      <Stack direction="row">
        <ButtonsNavigate />
      </Stack>
      <Flex align="center" ml="auto">
        <Profile />
      </Flex>
    </Flex>
  );
};
