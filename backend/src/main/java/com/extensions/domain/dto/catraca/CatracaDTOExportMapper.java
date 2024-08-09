package com.extensions.domain.dto.catraca;

import com.extensions.domain.entity.Catraca;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CatracaDTOExportMapper implements Function<Catraca, CatracaDTOExport> {
    @Override
    public CatracaDTOExport apply(Catraca catraca) {
        return new CatracaDTOExport(
                catraca.getNome(),
                catraca.getIp(),
                catraca.getCom(),
                catraca.getMac(),
                catraca.getNumeroDoEquipamento(),
                catraca.getNumeroDeSerie()
        );
    }
}
