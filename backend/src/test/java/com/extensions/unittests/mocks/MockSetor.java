package com.extensions.unittests.mocks;

import com.extensions.domain.dto.setor.SetorDTO;
import com.extensions.domain.entity.Setor;

public class MockSetor {


    public Setor mockEntity(String id) {
        Setor setor = new Setor();
        setor.setId(id);
        setor.setNome("TI " + id);
        return setor;
    }

    public SetorDTO mockDTO(String id) {
        SetorDTO dto = new SetorDTO();
        dto.setId(id);
        dto.setNome("TI " + id);
        return dto;
    }

}
