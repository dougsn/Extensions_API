package com.extensions.integrationtests.wrappers.impressoras;

import com.extensions.integrationtests.dto.impressora.ImpressoraDTOTest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ImpressoraEmbeddedDTO implements Serializable {
    @JsonProperty("impressoraDTOList")
    private List<ImpressoraDTOTest> impressoras;

    public ImpressoraEmbeddedDTO() {
    }

    public ImpressoraEmbeddedDTO(List<ImpressoraDTOTest> impressoras) {
        this.impressoras = impressoras;
    }

    public List<ImpressoraDTOTest> getimpressoras() {
        return impressoras;
    }

    public void setimpressoras(List<ImpressoraDTOTest> impressoras) {
        this.impressoras = impressoras;
    }
}
