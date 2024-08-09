package com.extensions.domain.dto.email;

import java.io.Serializable;

public class EmailDTOExport implements Serializable {
    private String setor;
    private String conta;
    private String senha;

    public EmailDTOExport() {
    }

    public EmailDTOExport(String setor, String conta, String senha) {
        this.setor = setor;
        this.conta = conta;
        this.senha = senha;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
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
}
