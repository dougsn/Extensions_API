package com.extensions.domain.dto.catraca;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


public class CatracaDTOSwagger implements Serializable {
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    private String nome;
    @Schema(type = "string", example = "192.168.0.1")
    private String ip;
    @Schema(type = "string", example = "23X13B")
    private String com;
    @Schema(type = "string", example = "23X13B")
    private String mac;
    @Schema(type = "string", example = "23X13B")
    @JsonProperty("numero_do_equipamento")
    private String numeroDoEquipamento;
    @Schema(type = "string", example = "23X13B")
    @JsonProperty("numero_de_serie")
    private String numeroDeSerie;

    public CatracaDTOSwagger() {
    }

    public CatracaDTOSwagger(String id, String nome, String ip, String com, String mac, String numeroDoEquipamento, String numeroDeSerie) {
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
