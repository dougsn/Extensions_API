package com.extensions.domain.dto.projeto;

import com.extensions.domain.entity.Projeto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProjetoDTOExportMapper implements Function<Projeto, ProjetoDTOExport> {


    @Override
    public ProjetoDTOExport apply(Projeto projeto) {
        return new ProjetoDTOExport(
                projeto.getStatus().getNome(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getCreatedBy(),
                projeto.getCreatedAt(),
                projeto.getUpdatedBy(),
                projeto.getUpdatedAt()
        );

    }
}
