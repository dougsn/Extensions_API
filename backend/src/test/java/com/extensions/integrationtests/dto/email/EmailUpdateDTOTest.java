package com.extensions.integrationtests.dto.email;

import java.io.Serializable;

public class EmailUpdateDTOTest implements Serializable {

    private String id;

    private String conta;

    private String senha;

    private String id_setor;

    public EmailUpdateDTOTest() {
    }


    public EmailUpdateDTOTest(String id, String conta, String senha, String id_setor) {
        this.id = id;
        this.conta = conta;
        this.senha = senha;
        this.id_setor = id_setor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

public String getId_setor() {
        return id_setor;
    }

    public void setId_setor(String id_setor) {
        this.id_setor = id_setor;
    }
}
