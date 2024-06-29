package com.extensions.domain.dto.status;

import com.extensions.domain.entity.Setor;
import com.extensions.domain.entity.Status;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StatusDTOMapper implements Function<Status, StatusDTO> {
    @Override
    public StatusDTO apply(Status status) {
        return new StatusDTO(
                status.getId(),
                status.getNome()
        );
    }
}
