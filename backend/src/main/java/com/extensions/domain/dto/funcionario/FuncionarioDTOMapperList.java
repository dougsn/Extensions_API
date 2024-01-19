package com.extensions.domain.dto.funcionario;

import com.extensions.domain.entity.Funcionario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class FuncionarioDTOMapperList implements Function<List<Funcionario>, List<FuncionarioDTO>> {


    @Override
    public List<FuncionarioDTO> apply(List<Funcionario> funcionarios) {
        List<FuncionarioDTO> dtoList = new ArrayList<>();
        funcionarios.forEach(func -> {
            FuncionarioDTO dto = new FuncionarioDTO(func.getId(), func.getNome(), func.getRamal(), func.getEmail(),
                    func.getSetor().getId(), func.getSetor().getNome());
            dtoList.add(dto);
        });
        return dtoList;

    }
}
