package com.extensions.integrationtests.wrappers.funcionario;

import com.extensions.integrationtests.dto.funcionario.FuncionarioDTOTest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class FuncionarioEmbeddedDTO implements Serializable {
    @JsonProperty("funcionarioDTOList")
    private List<FuncionarioDTOTest> funcionarios;

    public FuncionarioEmbeddedDTO() {
    }

    public List<FuncionarioDTOTest> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioDTOTest> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
