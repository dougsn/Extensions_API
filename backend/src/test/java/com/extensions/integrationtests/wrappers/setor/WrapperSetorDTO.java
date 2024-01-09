package com.extensions.integrationtests.wrappers.setor;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperSetorDTO implements Serializable {
    @JsonProperty("_embedded")
    private SetorEmbeddedDTO embeded;

    public WrapperSetorDTO() {
    }

    public SetorEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(SetorEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
