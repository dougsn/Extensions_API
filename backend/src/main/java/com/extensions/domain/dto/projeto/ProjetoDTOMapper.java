package com.extensions.domain.dto.projeto;

import com.extensions.domain.entity.Projeto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProjetoDTOMapper implements Function<Projeto, ProjetoDTO> {


    @Override
    public ProjetoDTO apply(Projeto projeto) {
        return new ProjetoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getStatus().getId(),
                projeto.getStatus().getNome()
        );

    }
}
