package com.extensions.domain.dto.antena;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AntenaDTOExport implements Serializable {
    private String local;
    private String modelo;
    @JsonProperty("tipo_antena")
    private String tipoAntena;
    private String ip;
    private String localizacao;
    private String ssid;
    private String senha;

    public AntenaDTOExport() {
    }

    public AntenaDTOExport(String local, String modelo, String tipoAntena, String ip, String localizacao, String ssid, String senha) {
        this.local = local;
        this.modelo = modelo;
        this.tipoAntena = tipoAntena;
        this.ip = ip;
        this.localizacao = localizacao;
        this.ssid = ssid;
        this.senha = senha;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoAntena() {
        return tipoAntena;
    }

    public void setTipoAntena(String tipoAntena) {
        this.tipoAntena = tipoAntena;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
