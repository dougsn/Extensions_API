package com.extensions.domain.dto.funcionario;

import com.extensions.domain.dto.setor.SetorDTOMapper;
import com.extensions.domain.entity.Funcionario;
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
