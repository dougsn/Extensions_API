package com.extensions.unittests.mocks;

import com.extensions.domain.dto.setor.SetorDTO;
import com.extensions.domain.entity.Setor;

public class MockSetor {

    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";


    public Setor mockEntity() {
        return new Setor(UUID_MOCK, "TI");
    }

    public SetorDTO mockDTO() {
        return new SetorDTO(UUID_MOCK, "TI");
    }

}
