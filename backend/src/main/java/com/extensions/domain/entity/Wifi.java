package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "wifi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Wifi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String ip;
    private String usuario;
    @Column(name = "senha_browser")
    private String senhaBrowser;
    private String ssid;
    @Column(name = "senha_wifi")
    private String senhaWifi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_setores")
    private Setor setor;

    public Wifi() {
    }

    public Wifi(String id, String ip, String usuario, String senhaBrowser, String ssid, String senhaWifi, Setor setor) {
        this.id = id;
        this.ip = ip;
        this.usuario = usuario;
        this.senhaBrowser = senhaBrowser;
        this.ssid = ssid;
        this.senhaWifi = senhaWifi;
        this.setor = setor;
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

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
