package com.extensions.integrationtests.wrappers.email;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperEmailDTO implements Serializable {
    @JsonProperty("_embedded")
    private EmailEmbeddedDTO embeded;

    public WrapperEmailDTO() {
    }

    public EmailEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(EmailEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
