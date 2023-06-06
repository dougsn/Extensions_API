package com.extensions.DTO.funcionario;

import com.extensions.DTO.setor.SetorDTOMapper;
import com.extensions.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FuncionarioDTOMapper implements Function<Funcionario, FuncionarioDTO> {

    @Autowired
    SetorDTOMapper setorDTOMapper;


    @Override
    public FuncionarioDTO apply(Funcionario funcionario) {
        try {
            return new FuncionarioDTO(
                    funcionario.getId(),
                    funcionario.getName(),
                    funcionario.getRamal(),
                    funcionario.getEmail(),
                    setorDTOMapper.apply(funcionario.getSetor())
            );
        } catch (Exception e) {
            return new FuncionarioDTO(
                    funcionario.getId(),
                    funcionario.getName(),
                    funcionario.getRamal(),
                    funcionario.getEmail(),
                    null
            );
        }
    }
}
