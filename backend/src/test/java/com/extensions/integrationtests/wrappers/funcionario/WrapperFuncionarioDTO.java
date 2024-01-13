package com.extensions.integrationtests.wrappers.funcionario;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperFuncionarioDTO implements Serializable {
    @JsonProperty("_embedded")
    private FuncionarioEmbeddedDTO embeded;

    public WrapperFuncionarioDTO() {
    }

    public FuncionarioEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(FuncionarioEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
