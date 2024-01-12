package com.extensions.domain.dto.funcionario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public class FuncionarioDTOSwagger implements Serializable {
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [nome] é obrigatório.")
    private String nome;
    @Schema(type = "string", example = "262 ...")
    @NotEmpty(message = "O campo [ramal] é obrigatório.")
    private String ramal;
    @Schema(type = "string", example = "johndoe@gmail.com ...")
    @NotEmpty(message = "O campo [email] é obrigatório.")
    private String email;
    @Schema(type = "string", example = "TI")
    @NotEmpty(message = "O campo [setor] é obrigatório.")
    private String idSetor;

    public FuncionarioDTOSwagger() {
    }

    public FuncionarioDTOSwagger(String id, String nome, String ramal, String email, String idSetor) {
        this.id = id;
        this.nome = nome;
        this.ramal = ramal;
        this.email = email;
        this.idSetor = idSetor;
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

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }
}
