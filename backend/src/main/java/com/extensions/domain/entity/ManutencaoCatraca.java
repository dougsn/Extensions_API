package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "manutencao_catraca")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ManutencaoCatraca implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDate dia;
    private String defeito;
    private String observacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_catracas")
    private Catraca catraca;
    private String created_by;
    private LocalDateTime updated_at;
    private String updated_by;

    public ManutencaoCatraca() {
    }

    public ManutencaoCatraca(String id, LocalDate dia, String defeito, String observacao, Catraca catraca, String created_by, LocalDateTime updated_at, String updated_by) {
        this.id = id;
        this.dia = dia;
        this.defeito = defeito;
        this.observacao = observacao;
        this.catraca = catraca;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Catraca getCatraca() {
        return catraca;
    }

    public void setCatraca(Catraca catraca) {
        this.catraca = catraca;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }
}
