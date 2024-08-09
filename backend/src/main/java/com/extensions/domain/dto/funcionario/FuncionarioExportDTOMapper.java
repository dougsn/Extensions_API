package com.extensions.domain.dto.funcionario;

import com.extensions.domain.entity.Funcionario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FuncionarioExportDTOMapper implements Function<Funcionario, FuncionarioDTOExport> {


    @Override
    public FuncionarioDTOExport apply(Funcionario funcionario) {
        return new FuncionarioDTOExport(
                funcionario.getSetor().getNome(),
                funcionario.getNome(),
                funcionario.getRamal(),
                funcionario.getEmail()
        );

    }
}
