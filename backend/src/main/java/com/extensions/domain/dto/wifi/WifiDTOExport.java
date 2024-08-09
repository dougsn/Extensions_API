package com.extensions.domain.dto.wifi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WifiDTOExport implements Serializable {
    private String setor;
    private String ip;
    private String usuario;
    @JsonProperty("senha_browser")
    private String senhaBrowser;
    private String ssid;
    @JsonProperty("senha_wifi")
    private String senhaWifi;

    public WifiDTOExport() {
    }

    public WifiDTOExport(String setor, String ip, String usuario, String senhaBrowser, String ssid, String senhaWifi) {
        this.setor = setor;
        this.ip = ip;
        this.usuario = usuario;
        this.senhaBrowser = senhaBrowser;
        this.ssid = ssid;
        this.senhaWifi = senhaWifi;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenhaBrowser() {
        return senhaBrowser;
    }

    public void setSenhaBrowser(String senhaBrowser) {
        this.senhaBrowser = senhaBrowser;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSenhaWifi() {
        return senhaWifi;
    }

    public void setSenhaWifi(String senhaWifi) {
        this.senhaWifi = senhaWifi;
    }
}
