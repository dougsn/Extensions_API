package com.extensions.domain.dto.setor;

import com.extensions.domain.entity.Setor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SetorDTOMapper implements Function<Setor, SetorDTO> {
    @Override
    public SetorDTO apply(Setor setor) {
        return new SetorDTO(
                setor.getId(),
                setor.getNome()
        );
    }
}
