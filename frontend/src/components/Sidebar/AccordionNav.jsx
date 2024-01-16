import PropTypes from "prop-types";
import { AccordionSection } from "./AccordionSection";
import { NavLink } from "./NavLink";
import { Accordion, Stack } from "@chakra-ui/react";
import {
  BsFillClipboard2CheckFill,
  BsFillPeopleFill,
  BsFillPersonFill,
  BsTelephoneFill,
} from "react-icons/bs";
import { Link, useLocation } from "react-router-dom";
import { useContext } from "react";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";

export const AccordionNav = ({ close }) => {
  const location = useLocation();
  const { userData } = useContext(AuthenticationContext);

  const checkUrl = (url, place) => {
    const part = url.split("/");
    if (part[1] === place) {
      return true;
    }

    return false;
  };
  const renderLinksBasedOnPermissions = () => {
    if (
      userData.permissions.some((p) => p.description === "ADMIN") ||
      userData.permissions.some((p) => p.description === "MANAGER")
    ) {
      return (
        <Link to="/user" onClick={close}>
          <NavLink
            as="a"
            icon={BsFillPeopleFill}
            active={checkUrl(location.pathname, "user")}
          >
            Todos Usuários
          </NavLink>
        </Link>
      );
    } else {
      return (
        <Link to={`/user/detail/${userData.id}`}>
          <NavLink icon={BsFillPersonFill}>Meus Dados</NavLink>
        </Link>
      );
    }
  };

  return (
    <Stack spacing="8" align="flex-start">
      <Accordion allowMultiple w="64" defaultIndex={[0]}>
        <AccordionSection title="RAMAL">
          <Link to="/ramais" onClick={close}>
            <NavLink
              as="a"
              icon={BsTelephoneFill}
              active={checkUrl(location.pathname, "ramais")}
            >
              Ramais
            </NavLink>
          </Link>
        </AccordionSection>
        <AccordionSection title="USUÁRIOS">
          {renderLinksBasedOnPermissions()}
        </AccordionSection>
      </Accordion>
    </Stack>
  );
};

AccordionNav.propTypes = {
  close: PropTypes.func,
};
