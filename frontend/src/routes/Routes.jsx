import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Flex } from "@chakra-ui/react";
import { ProtectedRoute } from "../components/ProtectedRoute";

// Components

import { Header } from "../components/Header";
import { Sidebar } from "../components/Sidebar";
import { AuthenticationProvider } from "../provider/AuthenticationProvider";

// Home
import { Home } from "../pages/home";

// Login
import { LoginForm } from "../pages/login";
import { UsuarioList } from "../pages/usuario/ListUsuario";
import { CreateUsuario } from "../pages/usuario/CreateUsuario";
import { ModalDetailsUsuario } from "../pages/usuario/ModalDetailsUsuario";
import { UpdateUsuario } from "../pages/usuario/UpdateUsuario";
import { DeleteUsuario } from "../pages/usuario/DeleteUsuario";

const AppRoutes = () => {
  return (
    <AuthenticationProvider>
      <BrowserRouter>
        <Header />
        <Flex
          w="100%"
          mt="6"
          maxWidth={1480}
          mx="auto"
          p="6"
          justifyContent={"center"}
        >
          <Routes>
            <Route path="" element={<LoginForm />} />
            <Route path="/home" element={<Home />} />

            {/* Gerenciamento de Usuários */}
            <Route
              path="/user"
              element={
                <ProtectedRoute>
                  <UsuarioList />
                </ProtectedRoute>
              }
            />
            <Route
              path="/user/new"
              element={
                <ProtectedRoute>
                  <CreateUsuario />
                </ProtectedRoute>
              }
            />

            <Route path="/user/detail/:id" element={<ModalDetailsUsuario />} />
            <Route path="/user/update/:id" element={<UpdateUsuario />} />

            <Route
              path="/user/delete/:id"
              element={
                <ProtectedRoute>
                  <DeleteUsuario />
                </ProtectedRoute>
              }
            />
          </Routes>
        </Flex>
      </BrowserRouter>
    </AuthenticationProvider>
  );
};

export default AppRoutes;
