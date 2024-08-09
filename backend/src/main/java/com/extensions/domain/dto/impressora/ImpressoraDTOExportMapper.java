package com.extensions.domain.dto.impressora;

import com.extensions.domain.entity.Impressora;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ImpressoraDTOExportMapper implements Function<Impressora, ImpressoraDTOExport> {


    @Override
    public ImpressoraDTOExport apply(Impressora impressora) {
        return new ImpressoraDTOExport(
                impressora.getSetor().getNome(),
                impressora.getMarca(),
                impressora.getModelo(),
                impressora.getIp(),
                impressora.getTonner(),
                impressora.getObservacao()
        );

    }
}
