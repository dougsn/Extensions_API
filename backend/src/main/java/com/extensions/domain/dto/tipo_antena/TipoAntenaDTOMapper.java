package com.extensions.domain.dto.tipo_antena;

import com.extensions.domain.entity.TipoAntena;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TipoAntenaDTOMapper implements Function<TipoAntena, TipoAntenaDTO> {
    @Override
    public TipoAntenaDTO apply(TipoAntena tipoAntena) {
        return new TipoAntenaDTO(
                tipoAntena.getId(),
                tipoAntena.getNome()
        );
    }
}
