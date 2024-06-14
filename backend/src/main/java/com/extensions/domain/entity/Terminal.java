package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "terminal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Terminal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String usuario;
    private String modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_setores")
    private Setor setor;

    public Terminal() {
    }

    public Terminal(String id, String usuario, String modelo, Setor setor) {
        this.id = id;
        this.usuario = usuario;
        this.modelo = modelo;
        this.setor = setor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
