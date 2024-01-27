package com.extensions.domain.dto.computador;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ComputadorUpdateDTO extends RepresentationModel<ComputadorUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "info_02 ...")
    @NotEmpty(message = "O campo [hostname] é obrigatório.")
    @Length(max = 50, message = "O campo [hostname] não pode ser maior que 50 caracteres")
    private String hostname;
    @Schema(type = "string", example = "Dell ...")
    @NotEmpty(message = "O campo [modelo] é obrigatório.")
    @Length(max = 15, message = "O campo [modelo] não pode ser maior que 15 caracteres")
    private String modelo;
    @Schema(type = "string", example = "Core i5 ...")
    @NotEmpty(message = "O campo [cpu] é obrigatório.")
    @Length(max = 15, message = "O campo [cpu] não pode ser maior que 15 caracteres")
    private String cpu;
    @Schema(type = "string", example = "4GB Ram ...")
    @NotEmpty(message = "O campo [memoria] é obrigatório.")
    @Length(max = 15, message = "O campo [memoria] não pode ser maior que 15 caracteres")
    private String memoria;
    @Schema(type = "string", example = "SSD 240GB ...")
    @NotEmpty(message = "O campo [disco] é obrigatório.")
    @Length(max = 15, message = "O campo [disco] não pode ser maior que 15 caracteres")
    private String disco;
    @Schema(type = "string", example = "Windows 10 ...")
    @NotEmpty(message = "O campo [sistema_operacional] é obrigatório.")
    @Length(max = 15, message = "O campo [sistema_operacional] não pode ser maior que 15 caracteres")
    @JsonProperty("sistema_operacional")
    private String SistemaOperacional;
    @Schema(type = "string", example = "Computador de John Doe ...")
    @NotEmpty(message = "O campo [observacao] é obrigatório.")
    private String observacao;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_setor] é obrigatório.")
    @JsonProperty("id_setor")
    private String idSetor;

    public ComputadorUpdateDTO() {
    }


    public ComputadorUpdateDTO(String id, String hostname, String modelo, String cpu, String memoria, String disco, String sistemaOperacional, String observacao, String idSetor) {
        this.id = id;
        this.hostname = hostname;
        this.modelo = modelo;
        this.cpu = cpu;
        this.memoria = memoria;
        this.disco = disco;
        SistemaOperacional = sistemaOperacional;
        this.observacao = observacao;
        this.idSetor = idSetor;
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

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }
}