package com.extensions.integrationtests.dto.impressora;

import java.io.Serializable;

public class ImpressoraUpdateDTOTest implements Serializable {
    private String id;
    private String marca;
    private String modelo;
    private String ip;
    private String tonner;
    private String observacao;

    private String id_setor;

    public ImpressoraUpdateDTOTest() {
    }

    public ImpressoraUpdateDTOTest(String id, String marca, String modelo, String ip, String tonner, String observacao, String id_setor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ip = ip;
        this.tonner = tonner;
        this.observacao = observacao;
        this.id_setor = id_setor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTonner() {
        return tonner;
    }

    public void setTonner(String tonner) {
        this.tonner = tonner;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getId_setor() {
        return id_setor;
    }

    public void setId_setor(String id_setor) {
        this.id_setor = id_setor;
    }
}
