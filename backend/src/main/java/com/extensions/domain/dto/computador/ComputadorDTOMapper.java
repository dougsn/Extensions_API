package com.extensions.domain.dto.computador;

import com.extensions.domain.entity.Computador;
import com.extensions.domain.entity.Funcionario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ComputadorDTOMapper implements Function<Computador, ComputadorDTO> {


    @Override
    public ComputadorDTO apply(Computador computador) {
        return new ComputadorDTO(
                computador.getId(),
                computador.getHostname(),
                computador.getModelo(),
                computador.getCpu(),
                computador.getMemoria(),
                computador.getDisco(),
                computador.getSistemaOperacional(),
                computador.getObservacao(),
                computador.getSetor().getId(),
                computador.getSetor().getNome()
        );

    }
}
