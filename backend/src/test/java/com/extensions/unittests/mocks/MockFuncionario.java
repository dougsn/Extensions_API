package com.extensions.unittests.mocks;

import com.extensions.domain.dto.funcionario.FuncionarioDTO;
import com.extensions.domain.dto.funcionario.FuncionarioUpdateDTO;
import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Setor;
import com.extensions.integrationtests.dto.setor.SetorDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class MockFuncionario {

    public List<Funcionario> mockEntityList(Setor setor) {
        List<Funcionario> funcionarios = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            funcionarios.add(mockEntity("ID" + i, setor));
        }
        return funcionarios;
    }
    public List<FuncionarioDTO> mockDTOList(String idSetor) {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            funcionarios.add(mockDTO("ID" + i, idSetor));
        }
        return funcionarios;
    }

    public Funcionario mockEntity(String id, Setor setor) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome("Douglas " + id);
        funcionario.setEmail("douglas@gmail.com");
        funcionario.setRamal("123");
        funcionario.setSetor(setor);
        return funcionario;
    }

    public FuncionarioDTO mockDTO(String id, String idSetor) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(id);
        dto.setNome("Douglas " + id);
        dto.setEmail("douglas@gmail.com");
        dto.setRamal("123");
        dto.setIdSetor(idSetor);
        return dto;
    }
    public FuncionarioUpdateDTO mockUpdateDTO(String id, String idSetor) {
        FuncionarioUpdateDTO dto = new FuncionarioUpdateDTO();
        dto.setId(id);
        dto.setNome("Douglas " + id);
        dto.setEmail("douglas@gmail.com");
        dto.setRamal("123");
        dto.setIdSetor(idSetor);
        return dto;
    }
}
