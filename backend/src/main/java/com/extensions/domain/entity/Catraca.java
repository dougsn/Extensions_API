package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity(name = "catracas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Catraca implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String ip;
    private String com;
    private String mac;
    @Column(name = "numero_do_equipamento")
    private String numeroDoEquipamento;
    @Column(name = "numero_de_serie")
    private String numeroDeSerie;

    public Catraca() {
    }

    public Catraca(String id, String nome, String ip, String com, String mac, String numeroDoEquipamento, String numeroDeSerie) {
        this.id = id;
        this.nome = nome;
        this.ip = ip;
        this.com = com;
        this.mac = mac;
        this.numeroDoEquipamento = numeroDoEquipamento;
        this.numeroDeSerie = numeroDeSerie;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNumeroDoEquipamento() {
        return numeroDoEquipamento;
    }

    public void setNumeroDoEquipamento(String numeroDoEquipamento) {
        this.numeroDoEquipamento = numeroDoEquipamento;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
