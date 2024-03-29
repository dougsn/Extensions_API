package com.extensions.domain.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class EmailDTO extends RepresentationModel<EmailDTO> implements Serializable {
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [conta] é obrigatório.")
    @Email(message = "Digite um e-mail válido.")
    @Length(max = 100, message = "O campo [conta] não pode ser maior que 100 caracteres")
    private String conta;
    @Schema(type = "string", example = "!senha#")
    @NotEmpty(message = "O campo [senha] é obrigatório.")
    @Length(max = 50, message = "O campo [senha] não pode ser maior que 50 caracteres")
    private String senha;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_setor] é obrigatório.")
    @JsonProperty("id_setor")
    private String idSetor;
    @Schema(type = "string", example = "Administração")
    @JsonProperty("nome_setor")
    private String nomeSetor;

    public EmailDTO() {
    }

    public EmailDTO(String id, String conta, String senha, String idSetor, String nomeSetor) {
        this.id = id;
        this.conta = conta;
        this.senha = senha;
        this.idSetor = idSetor;
        this.nomeSetor = nomeSetor;
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

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }
}
