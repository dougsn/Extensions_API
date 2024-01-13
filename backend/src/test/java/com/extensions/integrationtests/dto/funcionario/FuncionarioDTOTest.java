package com.extensions.integrationtests.dto.funcionario;

import java.io.Serializable;

public class FuncionarioDTOTest implements Serializable {
    private String id;

    private String nome;
    private String ramal;
    private String email;
    private String id_setor;

    public FuncionarioDTOTest() {
    }

    public FuncionarioDTOTest(String id, String nome, String ramal, String email, String id_setor) {
        this.id = id;
        this.nome = nome;
        this.ramal = ramal;
        this.email = email;
        this.id_setor = id_setor;
    }

    public String getId_setor() {
        return id_setor;
    }

    public void setId_setor(String id_setor) {
        this.id_setor = id_setor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
