package com.extensions.DTO.setor;

import com.extensions.entity.Setor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SetorDTOMapper implements Function<Setor, SetorDTO> {
    @Override
    public SetorDTO apply(Setor setor) {
        return new SetorDTO(
                setor.getId(),
                setor.getName()
        );
    }
}
