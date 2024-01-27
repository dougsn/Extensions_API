package com.extensions.domain.dto.impressora;

import com.extensions.domain.entity.Impressora;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ImpressoraDTOMapper implements Function<Impressora, ImpressoraDTO> {


    @Override
    public ImpressoraDTO apply(Impressora impressora) {
        return new ImpressoraDTO(
                impressora.getId(),
                impressora.getMarca(),
                impressora.getModelo(),
                impressora.getIp(),
                impressora.getTonner(),
                impressora.getObservacao(),
                impressora.getSetor().getId(),
                impressora.getSetor().getNome()

        );

    }
}
