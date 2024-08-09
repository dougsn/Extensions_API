package com.extensions.domain.dto.projeto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProjetoDTOExport implements Serializable {
    private String status;
    private String nome;
    private String descricao;

    @JsonProperty("criado_por")
    private String criadoPor;
    @JsonProperty("criado_as")
    private LocalDateTime criadoAs;
    @JsonProperty("atualizado_por")
    private String atualizadoPor;
    @JsonProperty("atualizado_as")
    private LocalDateTime atualizadoAs;

    public ProjetoDTOExport() {
    }

    public ProjetoDTOExport(String status, String nome, String descricao, String criadoPor, LocalDateTime criadoAs, String atualizadoPor, LocalDateTime atualizadoAs) {
        this.status = status;
        this.nome = nome;
        this.descricao = descricao;
        this.criadoPor = criadoPor;
        this.criadoAs = criadoAs;
        this.atualizadoPor = atualizadoPor;
        this.atualizadoAs = atualizadoAs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
