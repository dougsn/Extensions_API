package com.extensions.domain.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class TerminalUpdateDTO extends RepresentationModel<TerminalUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "Douglas Nascimento")
    @NotEmpty(message = "O campo [usuario] é obrigatório.")
    private String usuario;
    @Schema(type = "string", example = "Raspberry Pi 3")
    @NotEmpty(message = "O campo [modelo] é obrigatório.")
    private String modelo;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_setor] é obrigatório.")
    @JsonProperty("id_setor")
    private String idSetor;
    @Schema(type = "string", example = "Administração")
    @JsonProperty("nome_setor")
    private String nomeSetor;


    public TerminalUpdateDTO() {
    }


    public TerminalUpdateDTO(String id, String usuario, String modelo, String idSetor, String nomeSetor) {
        this.id = id;
        this.usuario = usuario;
        this.modelo = modelo;
        this.idSetor = idSetor;
        this.nomeSetor = nomeSetor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
