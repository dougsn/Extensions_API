package com.extensions.domain.dto.local;

import com.extensions.domain.entity.Local;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LocalDTOMapper implements Function<Local, LocalDTO> {
    @Override
    public LocalDTO apply(Local local) {
        return new LocalDTO(
                local.getId(),
                local.getNome()
        );
    }
}
