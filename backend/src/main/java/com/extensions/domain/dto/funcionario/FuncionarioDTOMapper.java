package com.extensions.domain.dto.funcionario;

import com.extensions.domain.entity.Funcionario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FuncionarioDTOMapper implements Function<Funcionario, FuncionarioDTO> {


    @Override
    public FuncionarioDTO apply(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getRamal(),
                funcionario.getEmail(),
                funcionario.getSetor().getId()
        );

    }
}
