package com.extensions.unittests.mocks;

import com.extensions.domain.dto.impressora.ImpressoraDTO;
import com.extensions.domain.dto.impressora.ImpressoraUpdateDTO;
import com.extensions.domain.entity.Impressora;
import com.extensions.domain.entity.Setor;

import java.util.ArrayList;
import java.util.List;


public class MockImpressora {

    public List<Impressora> mockEntityList(Setor setor) {
        List<Impressora> impressoras = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            impressoras.add(mockEntity("ID" + i, setor));
        }
        return impressoras;
    }

    public List<ImpressoraDTO> mockDTOList(String idSetor) {
        List<ImpressoraDTO> impressoras = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            impressoras.add(mockDTO("ID" + i, idSetor));
        }
        return impressoras;
    }

    public Impressora mockEntity(String id, Setor setor) {
        Impressora impressoras = new Impressora();
        impressoras.setId(id);
        impressoras.setMarca("HP");
        impressoras.setModelo("Tank");
        impressoras.setIp("192");
        impressoras.setTonner("Tonner Tank");
        impressoras.setObservacao("Impressora de Douglas");
        impressoras.setSetor(setor);
        return impressoras;
    }

    public ImpressoraDTO mockDTO(String id, String idSetor) {
        ImpressoraDTO dto = new ImpressoraDTO();
        dto.setId(id);
        dto.setId(id);
        dto.setMarca("HP");
        dto.setModelo("Tank");
        dto.setIp("192");
        dto.setTonner("Tonner Tank");
        dto.setObservacao("Impressora de Douglas");
        dto.setIdSetor(idSetor);
        return dto;
    }

    public ImpressoraUpdateDTO mockUpdateDTO(String id, String idSetor) {
        ImpressoraUpdateDTO dto = new ImpressoraUpdateDTO();
        dto.setId(id);
        dto.setId(id);
        dto.setMarca("HP");
        dto.setModelo("Tank");
        dto.setIp("192");
        dto.setTonner("Tonner Tank");
        dto.setObservacao("Impressora de Douglas");
        dto.setIdSetor(idSetor);
        return dto;
    }
}
