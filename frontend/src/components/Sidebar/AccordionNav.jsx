import PropTypes from "prop-types";
import { AccordionSection } from "./AccordionSection";
import { NavLink } from "./NavLink";
import { Accordion, Stack } from "@chakra-ui/react";
import {
  BsFillPeopleFill,
  BsPrinterFill,
  BsTelephoneFill,
} from "react-icons/bs";
import { Link, useLocation } from "react-router-dom";
import { useContext } from "react";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";
import { RiComputerFill, RiOrganizationChart } from "react-icons/ri";
import { MdEmail } from "react-icons/md";

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
        <>
          <Link to="/setor" onClick={close}>
            <NavLink
              as="a"
              icon={RiOrganizationChart}
              active={checkUrl(location.pathname, "setor")}
            >
              Setores
            </NavLink>
          </Link>
          <Link to="/email" onClick={close}>
            <NavLink
              as="a"
              icon={MdEmail}
              active={checkUrl(location.pathname, "email")}
            >
              Emails
            </NavLink>
          </Link>
          <Link to="/computador" onClick={close}>
            <NavLink
              as="a"
              icon={RiComputerFill}
              active={checkUrl(location.pathname, "computador")}
            >
              Computadores
            </NavLink>
          </Link>
          <Link to="/impressora" onClick={close}>
            <NavLink
              as="a"
              icon={BsPrinterFill}
              active={checkUrl(location.pathname, "impressora")}
            >
              Impressoras
            </NavLink>
          </Link>
          <Link to="/user" onClick={close}>
            <NavLink
              as="a"
              icon={BsFillPeopleFill}
              active={checkUrl(location.pathname, "user")}
            >
              Todos Usuários
            </NavLink>
          </Link>
        </>
      );
    }
  };

  return (
    <Stack spacing="8" align="flex-start">
      <Accordion allowMultiple w="64" defaultIndex={[0]}>
        <AccordionSection title="RAMAL">
          <Link to="/ramal" onClick={close}>
            <NavLink
              as="a"
              icon={BsTelephoneFill}
              active={checkUrl(location.pathname, "ramal")}
            >
              Ramais
            </NavLink>
          </Link>

          {renderLinksBasedOnPermissions()}
        </AccordionSection>
      </Accordion>
    </Stack>
  );
};

AccordionNav.propTypes = {
  close: PropTypes.func,
};
