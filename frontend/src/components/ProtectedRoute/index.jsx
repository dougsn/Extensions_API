import { useContext } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { AuthenticationContext } from "../../provider/AuthenticationProvider";

export const ProtectedRoute = (props) => {
  const { userData } = useContext(AuthenticationContext);
  const location = useLocation();

  const path = location.pathname.split("/");

  if (userData.role == "ADMIN") {
    return props.children;
  } else {
    return <Navigate to={`/${path[1]}`} />;
  }
};
