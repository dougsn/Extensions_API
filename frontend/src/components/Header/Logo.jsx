import { Image } from "@chakra-ui/react";
import icon from "../../../public/favicon.png";

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
