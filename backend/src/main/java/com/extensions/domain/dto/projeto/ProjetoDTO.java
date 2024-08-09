package com.extensions.domain.dto.projeto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProjetoDTO extends RepresentationModel<ProjetoDTO> implements Serializable {
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    private String id;
    @Schema(type = "string", example = "Catraca")
    @NotEmpty(message = "O campo [nome] é obrigatório.")
    private String nome;
    @Schema(type = "string", example = "Projeto das catracas para finalidade ...")
    @NotEmpty(message = "O campo [descricao] é obrigatório.")
    private String descricao;

    @Schema(type = "string", example = "Thiago")
    @JsonProperty("criado_por")
    private String criadoPor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(type = "string", example = "15/03/2023 12:30:20")
    @JsonProperty("criado_as")
    private LocalDateTime criadoAs;
    @Schema(type = "string", example = "Thiago")
    @JsonProperty("atualizado_por")
    private String atualizadoPor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(type = "string", example = "15/03/2023 12:30:20")
    @JsonProperty("atualizado_as")
    private LocalDateTime atualizadoAs;

    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_status] é obrigatório.")
    @JsonProperty("id_status")
    private String idStatus;
    @Schema(type = "string", example = "Finalizado")
    @JsonProperty("nome_status")
    private String nomeStatus;

    public ProjetoDTO() {
    }

    public ProjetoDTO(String id, String nome, String descricao, String criadoPor, LocalDateTime criadoAs, String atualizadoPor, LocalDateTime atualizadoAs, String idStatus, String nomeStatus) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.criadoPor = criadoPor;
        this.criadoAs = criadoAs;
        this.atualizadoPor = atualizadoPor;
        this.atualizadoAs = atualizadoAs;
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

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public LocalDateTime getCriadoAs() {
        return criadoAs;
    }

    public void setCriadoAs(LocalDateTime criadoAs) {
        this.criadoAs = criadoAs;
    }

    public String getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(String atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    public LocalDateTime getAtualizadoAs() {
        return atualizadoAs;
    }

    public void setAtualizadoAs(LocalDateTime atualizadoAs) {
        this.atualizadoAs = atualizadoAs;
    }
}
