package com.extensions.integrationtests.dto.computador;

import java.io.Serializable;

public class ComputadorDTOTest implements Serializable {
    private String id;

    private String hostname;

    private String modelo;
    private String cpu;
    private String memoria;
    private String disco;
    private String SistemaOperacional;
    private String observacao;
    private String id_setor;

    public ComputadorDTOTest() {
    }

    public ComputadorDTOTest(String id, String hostname, String modelo, String cpu, String memoria, String disco, String sistemaOperacional, String observacao, String id_setor) {
        this.id = id;
        this.hostname = hostname;
        this.modelo = modelo;
        this.cpu = cpu;
        this.memoria = memoria;
        this.disco = disco;
        SistemaOperacional = sistemaOperacional;
        this.observacao = observacao;
        this.id_setor = id_setor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return SistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        SistemaOperacional = sistemaOperacional;
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
