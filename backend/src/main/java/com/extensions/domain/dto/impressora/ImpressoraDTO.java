package com.extensions.domain.dto.impressora;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ImpressoraDTO extends RepresentationModel<ImpressoraDTO> implements Serializable {
    private String id;
    @Schema(type = "string", example = "HP ...")
    @NotEmpty(message = "O campo [marca] é obrigatório.")
    @Length(max = 20, message = "O campo [marca] não pode ser maior que 20 caracteres")
    private String marca;
    @Schema(type = "string", example = "Tank ...")
    @NotEmpty(message = "O campo [modelo] é obrigatório.")
    @Length(max = 20, message = "O campo [modelo] não pode ser maior que 20 caracteres")
    private String modelo;
    @Schema(type = "string", example = "192.168.0.1")
    @NotEmpty(message = "O campo [ip] é obrigatório.")
    @Length(max = 50, message = "O campo [ip] não pode ser maior que 50 caracteres")
    private String ip;
    @Schema(type = "string", example = "205U ...")
    @NotEmpty(message = "O campo [tonner] é obrigatório.")
    private String tonner;
    @Schema(type = "string", example = "Computador de John Doe ...")
    @NotEmpty(message = "O campo [observacao] é obrigatório.")
    private String observacao;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_setor] é obrigatório.")
    @JsonProperty("id_setor")
    private String idSetor;
    @Schema(type = "string", example = "Administração")
    @JsonProperty("nome_setor")
    private String nomeSetor;

    public ImpressoraDTO() {
    }

    public ImpressoraDTO(String id, String marca, String modelo, String ip, String tonner, String observacao, String idSetor, String nomeSetor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ip = ip;
        this.tonner = tonner;
        this.observacao = observacao;
        this.idSetor = idSetor;
        this.nomeSetor = nomeSetor;
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
