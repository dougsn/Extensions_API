package com.extensions.domain.dto.computador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"setor", "hostname", "modelo", "cpu", "memoria", "disco", "observacao", "sistema_operacional"})
public class ComputadorDTOExport implements Serializable {
    private String setor;
    private String hostname;
    private String modelo;
    private String cpu;
    private String memoria;
    private String disco;
    @JsonProperty("sistema_operacional")
    private String sistemaOperacional;
    private String observacao;

    public ComputadorDTOExport() {
    }

    public ComputadorDTOExport(String setor, String hostname, String modelo, String cpu, String memoria, String disco, String sistemaOperacional, String observacao) {
        this.setor = setor;
        this.hostname = hostname;
        this.modelo = modelo;
        this.cpu = cpu;
        this.memoria = memoria;
        this.disco = disco;
        this.sistemaOperacional = sistemaOperacional;
        this.observacao = observacao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
