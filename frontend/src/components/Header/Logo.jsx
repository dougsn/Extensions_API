import { Image } from "@chakra-ui/react";
import icon from "../../assets/favicon.png";

export const Logo = () => {
  return (
    <Image
      borderRadius="lg"
      boxSize="30px"
      src={icon}
      alt="Logo Renave"
      mr={10}
    />
  );
};
