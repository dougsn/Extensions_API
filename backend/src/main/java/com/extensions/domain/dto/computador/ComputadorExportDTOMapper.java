package com.extensions.domain.dto.computador;

import com.extensions.domain.entity.Computador;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ComputadorExportDTOMapper implements Function<Computador, ComputadorDTOExport> {
    @Override
    public ComputadorDTOExport apply(Computador computador) {
        return new ComputadorDTOExport(
                computador.getSetor().getNome(),
                computador.getHostname(),
                computador.getModelo(),
                computador.getCpu(),
                computador.getMemoria(),
                computador.getDisco(),
                computador.getSistemaOperacional(),
                computador.getObservacao()
        );
    }
}
