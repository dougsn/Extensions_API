package com.extensions.integrationtests.wrappers.impressoras;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperImpressoraDTO implements Serializable {
    @JsonProperty("_embedded")
    private ImpressoraEmbeddedDTO embeded;

    public WrapperImpressoraDTO() {
    }

    public ImpressoraEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(ImpressoraEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
