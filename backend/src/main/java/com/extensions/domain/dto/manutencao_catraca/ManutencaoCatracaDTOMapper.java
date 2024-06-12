package com.extensions.domain.dto.manutencao_catraca;

import com.extensions.domain.entity.ManutencaoCatraca;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ManutencaoCatracaDTOMapper implements Function<ManutencaoCatraca, ManutencaoCatracaDTO> {


    @Override
    public ManutencaoCatracaDTO apply(ManutencaoCatraca manutencaoCatraca) {
        return new ManutencaoCatracaDTO(
                manutencaoCatraca.getId(),
                manutencaoCatraca.getDia(),
                manutencaoCatraca.getDefeito(),
                manutencaoCatraca.getObservacao(),
                manutencaoCatraca.getCatraca().getId(),
                manutencaoCatraca.getCatraca().getNome()
        );

    }
}
