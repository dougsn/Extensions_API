package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "projeto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Projeto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String descricao;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_status")
    private Status status;

    public Projeto() {
    }

    public Projeto(String id, String nome, String descricao, String createdBy, String updatedBy, LocalDate createdAt, LocalDate updatedAt, Status status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
