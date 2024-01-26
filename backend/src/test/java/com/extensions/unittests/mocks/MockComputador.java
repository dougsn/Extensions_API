package com.extensions.unittests.mocks;

import com.extensions.domain.dto.computador.ComputadorDTO;
import com.extensions.domain.dto.computador.ComputadorUpdateDTO;
import com.extensions.domain.entity.Computador;
import com.extensions.domain.entity.Setor;

import java.util.ArrayList;
import java.util.List;


public class MockComputador {

    public List<Computador> mockEntityList(Setor setor) {
        List<Computador> computadores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            computadores.add(mockEntity("ID" + i, setor));
        }
        return computadores;
    }

    public List<ComputadorDTO> mockDTOList(String idSetor) {
        List<ComputadorDTO> computadores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            computadores.add(mockDTO("ID" + i, idSetor));
        }
        return computadores;
    }

    public Computador mockEntity(String id, Setor setor) {
        Computador computador = new Computador();
        computador.setId(id);
        computador.setHostname("info_02");
        computador.setModelo("Dell");
        computador.setCpu("Core i5");
        computador.setMemoria("8GB RAM");
        computador.setDisco("SSD 240GB");
        computador.setSistemaOperacional("Windows 10");
        computador.setObservacao("Computador de Douglas");
        computador.setSetor(setor);
        return computador;
    }

    public ComputadorDTO mockDTO(String id, String idSetor) {
        ComputadorDTO dto = new ComputadorDTO();
        dto.setId(id);
        dto.setHostname("info_02");
        dto.setModelo("Dell");
        dto.setCpu("Core i5");
        dto.setMemoria("8GB RAM");
        dto.setDisco("SSD 240GB");
        dto.setSistemaOperacional("Windows 10");
        dto.setObservacao("Computador de Douglas");
        dto.setIdSetor(idSetor);
        return dto;
    }

    public ComputadorUpdateDTO mockUpdateDTO(String id, String idSetor) {
        ComputadorUpdateDTO dto = new ComputadorUpdateDTO();
        dto.setId(id);
        dto.setHostname("info_02");
        dto.setModelo("Dell");
        dto.setCpu("Core i5");
        dto.setMemoria("8GB RAM");
        dto.setDisco("SSD 240GB");
        dto.setSistemaOperacional("Windows 10");
        dto.setObservacao("Computador de Douglas");
        dto.setIdSetor(idSetor);
        return dto;
    }
}
