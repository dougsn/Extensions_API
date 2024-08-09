package com.extensions.domain.dto.funcionario;

import java.io.Serializable;

public class FuncionarioDTOExport implements Serializable {
    private String setor;
    private String nome;

    private String ramal;

    private String email;

    public FuncionarioDTOExport() {
    }

    public FuncionarioDTOExport(String setor, String nome, String ramal, String email) {
        this.setor = setor;
        this.nome = nome;
        this.ramal = ramal;
        this.email = email;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
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
