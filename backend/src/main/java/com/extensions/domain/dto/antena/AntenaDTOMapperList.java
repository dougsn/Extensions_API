package com.extensions.domain.dto.antena;

import com.extensions.domain.entity.Antena;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class AntenaDTOMapperList implements Function<List<Antena>, List<AntenaDTO>> {


    @Override
    public List<AntenaDTO> apply(List<Antena> antenas) {
        List<AntenaDTO> dtoList = new ArrayList<>();
        antenas.forEach(antena -> {
            AntenaDTO dto = new AntenaDTO(antena.getId(), antena.getIp(), antena.getLocalizacao(), antena.getSsid(),
                    antena.getSenha(), antena.getLocal().getId(), antena.getLocal().getNome(), antena.getModelo().getId(),
                    antena.getModelo().getNome(), antena.getTipoAntena().getId(), antena.getTipoAntena().getNome());
            dtoList.add(dto);
        });
        return dtoList;

    }
}
