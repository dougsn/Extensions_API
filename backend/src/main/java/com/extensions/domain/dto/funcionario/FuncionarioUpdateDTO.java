package com.extensions.domain.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class FuncionarioUpdateDTO extends RepresentationModel<FuncionarioUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [nome] é obrigatório.")
    @Length(max = 100, message = "O campo [nome] não pode ser maior que 100 caracteres")
    private String nome;
    @Schema(type = "string", example = "262 ...")
    @NotEmpty(message = "O campo [ramal] é obrigatório.")
    @Length(max = 7, message = "O campo [ramal] não pode ser maior que 7 caracteres")
    private String ramal;
    @Schema(type = "string", example = "johndoe@gmail.com ...")
    @NotEmpty(message = "O campo [email] é obrigatório.")
    @Length(max = 100, message = "O campo [email] não pode ser maior que 100 caracteres")
    @Email(message = "Email inválido")
    private String email;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_setor] é obrigatório.")
    @JsonProperty("id_setor")
    private String idSetor;

    public FuncionarioUpdateDTO() {
    }


    public FuncionarioUpdateDTO(String id, String nome, String ramal, String email, String idSetor) {
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
