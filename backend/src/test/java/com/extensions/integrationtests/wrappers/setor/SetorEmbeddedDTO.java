package com.extensions.integrationtests.wrappers.setor;

import com.extensions.integrationtests.dto.setor.SetorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class SetorEmbeddedDTO implements Serializable {
    @JsonProperty("setorDTOList")
    private List<SetorDTO> setores;

    public SetorEmbeddedDTO() {
    }

    public List<SetorDTO> getSetores() {
        return setores;
    }

    public void setSetores(List<SetorDTO> setores) {
        this.setores = setores;
    }
}
