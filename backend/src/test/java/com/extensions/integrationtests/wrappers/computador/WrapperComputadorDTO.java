package com.extensions.integrationtests.wrappers.computador;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperComputadorDTO implements Serializable {
    @JsonProperty("_embedded")
    private ComputadorEmbeddedDTO embeded;

    public WrapperComputadorDTO() {
    }

    public ComputadorEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(ComputadorEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
