package com.extensions.domain.dto.projeto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ProjetoDTO extends RepresentationModel<ProjetoDTO> implements Serializable {
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
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
    @Schema(type = "string", example = "Finalizado")
    @JsonProperty("nome_status")
    private String nomeStatus;

    public ProjetoDTO() {
    }

    public ProjetoDTO(String id, String nome, String descricao, String idStatus, String nomeStatus) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idStatus = idStatus;
        this.nomeStatus = nomeStatus;
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

    public String getNomeStatus() {
        return nomeStatus;
    }

    public void setNomeStatus(String nomeStatus) {
        this.nomeStatus = nomeStatus;
    }
}
