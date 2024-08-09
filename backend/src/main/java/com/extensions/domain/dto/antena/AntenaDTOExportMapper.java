package com.extensions.domain.dto.antena;

import com.extensions.domain.entity.Antena;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AntenaDTOExportMapper implements Function<Antena, AntenaDTOExport> {


    @Override
    public AntenaDTOExport apply(Antena antena) {
        return new AntenaDTOExport(
                antena.getLocal().getNome(),
                antena.getModelo().getNome(),
                antena.getTipoAntena().getNome(),
                antena.getIp(),
                antena.getLocalizacao(),
                antena.getSsid(),
                antena.getSenha()
        );

    }
}
