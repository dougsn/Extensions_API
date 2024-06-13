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
import { UpdateUsuario } from "../pages/usuario/UpdateUsuario";
import { DeleteUsuario } from "../pages/usuario/DeleteUsuario";

// Ramal
import { ListRamal } from "../pages/ramal/ListRamal";
import { CreateRamal } from "../pages/ramal/CreateRamal";
import { DeleteRamal } from "../pages/ramal/DeleteRamal";
import { UpdateRamal } from "../pages/ramal/UpdateRamal";

// Setor
import { ListSetor } from "../pages/setores/ListSetor";
import { CreateSetor } from "../pages/setores/CreateSetor";
import { DeleteSetor } from "../pages/setores/DeleteSetor";
import { UpdateSetor } from "../pages/setores/UpdateSetor";

// Catraca
import { ListCatraca } from "../pages/catraca/ListCatraca";
import { CreateCatraca } from "../pages/catraca/CreateCatraca";
import { DeleteCatraca } from "../pages/catraca/DeleteCatraca";
import { UpdateCatraca } from "../pages/catraca/UpdateCatraca";

// Manutenção das Catracas
import { ListManutencaoCatraca } from "../pages/manutencao_catraca/ListManutencaoCatraca";
import { CreateManutencaoCatraca } from "../pages/manutencao_catraca/CreateManutencaoCatraca";
import { UpdateManutencaoCatraca } from "../pages/manutencao_catraca/UpdateManutencaoCatraca";
import { DeleteManutencaoCatraca } from "../pages/manutencao_catraca/DeleteManutencaoCatraca";
import { ListManutencaoCatracaByDiaAndCatraca } from "../pages/manutencao_catraca/ListManutencaoCatracaByDiaAndCatraca ";

// Email
import { ListEmail } from "../pages/email/ListEmail";
import { CreateEmail } from "../pages/email/CreateEmail";
import { DeleteEmail } from "../pages/email/DeleteEmail";
import { UpdateEmail } from "../pages/email/UpdateEmail";

// Computador
import { ListComputador } from "../pages/computador/ListComputador";
import { CreateComputador } from "../pages/computador/CreateComputador";
import { DeleteComputador } from "../pages/computador/DeleteComputador";
import { UpdateComputador } from "../pages/computador/UpdateComputador";

// Impressora
import { ListImpressora } from "../pages/impressora/ListImpressora";
import { CreateImpressora } from "../pages/impressora/CreateImpressora";
import { DeleteImpressora } from "../pages/impressora/DeleteImpressora";
import { UpdateImpressora } from "../pages/impressora/UpdateImpressora";

// Tipo de antena
import { ListTipoAntena } from "../pages/tipoAntena/ListTipoAntena";
import { CreateTipoAntena } from "../pages/tipoAntena/CreateTipoAntena";
import { DeleteTipoAntena } from "../pages/tipoAntena/DeleteTipoAntena";
import { UpdateTipoAntena } from "../pages/tipoAntena/UpdateTipoAntena";

// Local
import { ListLocal } from "../pages/local/ListLocal";
import { CreateLocal } from "../pages/local/CreateLocal";
import { DeleteLocal } from "../pages/local/DeleteLocal";
import { UpdateLocal } from "../pages/local/UpdateLocal";

// Modelo
import { ListModelo } from "../pages/modelo/ListModelo";
import { CreateModelo } from "../pages/modelo/CreateModelo";
import { DeleteModelo } from "../pages/modelo/DeleteModelo";
import { UpdateModelo } from "../pages/modelo/UpdateModelo";

// Antena
import { ListAntena } from "../pages/antena/ListAntena";
import { CreateAntena } from "../pages/antena/CreateAntena";
import { DeleteAntena } from "../pages/antena/DeleteAntena";
import { UpdateAntena } from "../pages/antena/UpdateAntena";

const AppRoutes = () => {
  return (
    <>
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
              <Route path="/ramal" element={<ListRamal />} />

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

              {/* Gerenciamento de E-mails */}
              <Route
                path="/email"
                element={
                  <ProtectedRoute>
                    <ListEmail />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/email/new"
                element={
                  <ProtectedRoute>
                    <CreateEmail />
                  </ProtectedRoute>
                }
              />

              <Route path="/email/update/:id" element={<UpdateEmail />} />

              <Route
                path="/email/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteEmail />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento de E-mails */}
              <Route
                path="/computador"
                element={
                  <ProtectedRoute>
                    <ListComputador />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/computador/new"
                element={
                  <ProtectedRoute>
                    <CreateComputador />
                  </ProtectedRoute>
                }
              />

              <Route
                path="/computador/update/:id"
                element={<UpdateComputador />}
              />

              <Route
                path="/computador/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteComputador />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento de Impressoras */}
              <Route
                path="/impressora"
                element={
                  <ProtectedRoute>
                    <ListImpressora />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/impressora/new"
                element={
                  <ProtectedRoute>
                    <CreateImpressora />
                  </ProtectedRoute>
                }
              />

              <Route
                path="/impressora/update/:id"
                element={<UpdateImpressora />}
              />

              <Route
                path="/impressora/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteImpressora />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento de Tipos de Antena */}
              <Route
                path="/tipo-antena"
                element={
                  <ProtectedRoute>
                    <ListTipoAntena />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/tipo-antena/new"
                element={
                  <ProtectedRoute>
                    <CreateTipoAntena />
                  </ProtectedRoute>
                }
              />

              <Route
                path="/tipo-antena/update/:id"
                element={<UpdateTipoAntena />}
              />

              <Route
                path="/tipo-antena/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteTipoAntena />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento de Local */}
              <Route
                path="/local"
                element={
                  <ProtectedRoute>
                    <ListLocal />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/local/new"
                element={
                  <ProtectedRoute>
                    <CreateLocal />
                  </ProtectedRoute>
                }
              />

              <Route path="/local/update/:id" element={<UpdateLocal />} />

              <Route
                path="/local/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteLocal />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento do Modelo */}
              <Route
                path="/modelo"
                element={
                  <ProtectedRoute>
                    <ListModelo />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/modelo/new"
                element={
                  <ProtectedRoute>
                    <CreateModelo />
                  </ProtectedRoute>
                }
              />

              <Route path="/modelo/update/:id" element={<UpdateModelo />} />

              <Route
                path="/modelo/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteModelo />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento de Antena */}
              <Route
                path="/antena"
                element={
                  <ProtectedRoute>
                    <ListAntena />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/antena/new"
                element={
                  <ProtectedRoute>
                    <CreateAntena />
                  </ProtectedRoute>
                }
              />

              <Route path="/antena/update/:id" element={<UpdateAntena />} />

              <Route
                path="/antena/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteAntena />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento de Catracas */}
              <Route
                path="/catraca"
                element={
                  <ProtectedRoute>
                    <ListCatraca />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/catraca/new"
                element={
                  <ProtectedRoute>
                    <CreateCatraca />
                  </ProtectedRoute>
                }
              />

              <Route path="/catraca/update/:id" element={<UpdateCatraca />} />

              <Route
                path="/catraca/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteCatraca />
                  </ProtectedRoute>
                }
              />

              {/* Gerenciamento das Manutenções das Catracas */}
              <Route
                path="/manutencao-catraca"
                element={
                  <ProtectedRoute>
                    <ListManutencaoCatraca />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/relatorio-por-dia-e-catraca"
                element={
                  <ProtectedRoute>
                    <ListManutencaoCatracaByDiaAndCatraca />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/manutencao-catraca/new"
                element={
                  <ProtectedRoute>
                    <CreateManutencaoCatraca />
                  </ProtectedRoute>
                }
              />

              <Route
                path="/manutencao-catraca/update/:id"
                element={<UpdateManutencaoCatraca />}
              />

              <Route
                path="/manutencao-catraca/delete/:id"
                element={
                  <ProtectedRoute>
                    <DeleteManutencaoCatraca />
                  </ProtectedRoute>
                }
              />
            </Routes>
          </Flex>
        </BrowserRouter>
      </AuthenticationProvider>
    </>
  );
};

export default AppRoutes;
