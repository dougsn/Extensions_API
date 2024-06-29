package com.extensions.domain.dto.projeto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ProjetoUpdateDTO extends RepresentationModel<ProjetoUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "Catraca")
    @NotEmpty(message = "O campo [nome] é obrigatório.")
    private String nome;
    @Schema(type = "string", example = "Projeto das catracas para finalidade ...")
    @NotEmpty(message = "O campo [descricao] é obrigatório.")
    private String descricao;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_status] é obrigatório.")
    @JsonProperty("id_status")
    private String idStatus;

    public ProjetoUpdateDTO() {
    }

    public ProjetoUpdateDTO(String id, String nome, String descricao, String idStatus) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idStatus = idStatus;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }
}
