package com.extensions.domain.dto.antena;

import com.extensions.domain.entity.Antena;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AntenaDTOMapper implements Function<Antena, AntenaDTO> {


    @Override
    public AntenaDTO apply(Antena antena) {
        return new AntenaDTO(
                antena.getId(),
                antena.getIp(),
                antena.getLocalizacao(),
                antena.getSsid(),
                antena.getSenha(),
                antena.getLocal().getId(),
                antena.getLocal().getNome(),
                antena.getModelo().getId(),
                antena.getModelo().getNome(),
                antena.getTipoAntena().getId(),
                antena.getTipoAntena().getNome()
        );

    }
}
