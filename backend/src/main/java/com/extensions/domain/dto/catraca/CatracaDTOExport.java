package com.extensions.domain.dto.catraca;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class CatracaDTOExport implements Serializable {
    private String nome;
    private String ip;
    private String com;
    private String mac;
    @JsonProperty("numero_do_equipamento")
    private String numeroDoEquipamento;
    @JsonProperty("numero_de_serie")
    private String numeroDeSerie;

    public CatracaDTOExport() {
    }

    public CatracaDTOExport(String nome, String ip, String com, String mac, String numeroDoEquipamento, String numeroDeSerie) {
        this.nome = nome;
        this.ip = ip;
        this.com = com;
        this.mac = mac;
        this.numeroDoEquipamento = numeroDoEquipamento;
        this.numeroDeSerie = numeroDeSerie;
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
}
