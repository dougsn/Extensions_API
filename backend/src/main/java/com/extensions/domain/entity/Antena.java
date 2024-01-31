package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity(name = "antenas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Antena implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String ip;
    private String localizacao;
    private String ssid;
    private String senha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_locais")
    private Local local;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_modelos")
    private Modelo modelo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_tipo_antena")
    private TipoAntena tipoAntena;

    public Antena() {
    }

    public Antena(String id, String ip, String localizacao, String ssid, String senha, Local local, Modelo modelo, TipoAntena tipoAntena) {
        this.id = id;
        this.ip = ip;
        this.localizacao = localizacao;
        this.ssid = ssid;
        this.senha = senha;
        this.local = local;
        this.modelo = modelo;
        this.tipoAntena = tipoAntena;
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

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public TipoAntena getTipoAntena() {
        return tipoAntena;
    }

    public void setTipoAntena(TipoAntena tipoAntena) {
        this.tipoAntena = tipoAntena;
    }
}
