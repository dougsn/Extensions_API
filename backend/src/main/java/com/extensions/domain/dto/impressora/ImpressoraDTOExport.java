package com.extensions.domain.dto.impressora;

import java.io.Serializable;

public class ImpressoraDTOExport implements Serializable {
    private String setor;
    private String marca;
    private String modelo;
    private String ip;
    private String tonner;
    private String observacao;

    public ImpressoraDTOExport() {
    }

    public ImpressoraDTOExport(String setor, String marca, String modelo, String ip, String tonner, String observacao) {
        this.setor = setor;
        this.marca = marca;
        this.modelo = modelo;
        this.ip = ip;
        this.tonner = tonner;
        this.observacao = observacao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
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
}
