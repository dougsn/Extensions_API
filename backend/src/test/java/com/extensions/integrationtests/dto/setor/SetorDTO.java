package com.extensions.integrationtests.dto.setor;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


public class SetorDTO extends RepresentationModel<SetorDTO> implements Serializable {
    private String id;
    private String nome;

    public SetorDTO() {
    }

    public SetorDTO(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
