import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Flex } from "@chakra-ui/react";
import { ProtectedRoute } from "../components/ProtectedRoute";

// Components

import { Header } from "../components/Header";
import { AuthenticationProvider } from "../provider/AuthenticationProvider";

// Login
import { LoginForm } from "../pages/login";
import { ListUsuario } from "../pages/usuario/ListUsuario";
import { CreateUsuario } from "../pages/usuario/CreateUsuario";
import { ModalDetailsUsuario } from "../pages/usuario/ModalDetailsUsuario";
import { UpdateUsuario } from "../pages/usuario/UpdateUsuario";
import { DeleteUsuario } from "../pages/usuario/DeleteUsuario";

// Setor
import { ListSetor } from "../pages/setores/ListSetor";
import { CreateSetor } from "../pages/setores/CreateSetor";
import { DeleteSetor } from "../pages/setores/DeleteSetor";
import { UpdateSetor } from "../pages/setores/UpdateSetor";

// Ramal
import { ListRamal } from "../pages/ramal/ListRamal";
import { CreateRamal } from "../pages/ramal/CreateRamal";
import { DeleteRamal } from "../pages/ramal/DeleteRamal";
import { UpdateRamal } from "../pages/ramal/UpdateRamal";

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
            <Route path="/ramais" element={<ListRamal />} />

            {/* Gerenciamento de Usuários */}
            <Route
              path="/user"
              element={
                <ProtectedRoute>
                  <ListUsuario />
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

            {/* Gerenciamento de Setores */}
            <Route
              path="/setor"
              element={
                <ProtectedRoute>
                  <ListSetor />
                </ProtectedRoute>
              }
            />
            <Route
              path="/setor/new"
              element={
                <ProtectedRoute>
                  <CreateSetor />
                </ProtectedRoute>
              }
            />

            <Route path="/setor/update/:id" element={<UpdateSetor />} />

            <Route
              path="/setor/delete/:id"
              element={
                <ProtectedRoute>
                  <DeleteSetor />
                </ProtectedRoute>
              }
            />

            {/* Gerenciamento de Ramais */}
            <Route
              path="/ramal/new"
              element={
                <ProtectedRoute>
                  <CreateRamal />
                </ProtectedRoute>
              }
            />

            <Route path="/ramal/update/:id" element={<UpdateRamal />} />

            <Route
              path="/ramal/delete/:id"
              element={
                <ProtectedRoute>
                  <DeleteRamal />
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
