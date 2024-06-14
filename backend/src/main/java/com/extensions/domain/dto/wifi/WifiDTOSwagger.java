package com.extensions.domain.dto.wifi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public class WifiDTOSwagger implements Serializable {
    private String id;
    @Schema(type = "string", example = "192.168.0.1")
    @NotEmpty(message = "O campo [ip] é obrigatório.")
    private String ip;
    @Schema(type = "string", example = "Douglas Nascimento")
    @NotEmpty(message = "O campo [usuario] é obrigatório.")
    private String usuario;
    @Schema(type = "string", example = "!enavi08#")
    @NotEmpty(message = "O campo [senha_browser] é obrigatório.")
    @JsonProperty("senha_browser")
    private String senhaBrowser;
    @Schema(type = "string", example = "Armadores")
    @NotEmpty(message = "O campo [ssid] é obrigatório.")
    private String ssid;
    @Schema(type = "string", example = "!enavi08#")
    @NotEmpty(message = "O campo [senha_wifi] é obrigatório.")
    @JsonProperty("senha_wifi")
    private String senhaWifi;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_setor] é obrigatório.")
    @JsonProperty("id_setor")
    private String idSetor;
    @Schema(type = "string", example = "Administração")
    @JsonProperty("nome_setor")
    private String nomeSetor;

    public WifiDTOSwagger() {
    }

    public WifiDTOSwagger(String id, String ip, String usuario, String senhaBrowser, String ssid, String senhaWifi, String idSetor, String nomeSetor) {
        this.id = id;
        this.ip = ip;
        this.usuario = usuario;
        this.senhaBrowser = senhaBrowser;
        this.ssid = ssid;
        this.senhaWifi = senhaWifi;
        this.idSetor = idSetor;
        this.nomeSetor = nomeSetor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }
}
