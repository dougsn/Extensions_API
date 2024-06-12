package com.extensions.domain.dto.catraca;

import com.extensions.domain.entity.Catraca;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CatracaDTOMapper implements Function<Catraca, CatracaDTO> {
    @Override
    public CatracaDTO apply(Catraca catraca) {
        return new CatracaDTO(
                catraca.getId(),
                catraca.getNome(),
                catraca.getIp(),
                catraca.getCom(),
                catraca.getMac(),
                catraca.getNumeroDoEquipamento(),
                catraca.getNumeroDeSerie()
        );
    }
}
