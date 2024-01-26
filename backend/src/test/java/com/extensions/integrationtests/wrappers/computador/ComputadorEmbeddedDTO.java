package com.extensions.integrationtests.wrappers.computador;

import com.extensions.integrationtests.dto.computador.ComputadorDTOTest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ComputadorEmbeddedDTO implements Serializable {
    @JsonProperty("computadorDTOList")
    private List<ComputadorDTOTest> computadores;

    public ComputadorEmbeddedDTO() {
    }

    public ComputadorEmbeddedDTO(List<ComputadorDTOTest> computadores) {
        this.computadores = computadores;
    }

    public List<ComputadorDTOTest> getComputadores() {
        return computadores;
    }

    public void setComputadores(List<ComputadorDTOTest> computadores) {
        this.computadores = computadores;
    }
}
