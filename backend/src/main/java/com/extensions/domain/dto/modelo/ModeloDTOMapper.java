package com.extensions.domain.dto.modelo;

import com.extensions.domain.entity.Modelo;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ModeloDTOMapper implements Function<Modelo, ModeloDTO> {
    @Override
    public ModeloDTO apply(Modelo modelo) {
        return new ModeloDTO(
                modelo.getId(),
                modelo.getNome()
        );
    }
}
