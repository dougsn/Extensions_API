package com.extensions.domain.dto.terminal;

import com.extensions.domain.entity.Terminal;
import com.extensions.domain.entity.Wifi;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TerminalDTOMapper implements Function<Terminal, TerminalDTO> {


    @Override
    public TerminalDTO apply(Terminal terminal) {
        return new TerminalDTO(
                terminal.getId(),
                terminal.getUsuario(),
                terminal.getModelo(),
                terminal.getSetor().getId(),
                terminal.getSetor().getNome()
        );

    }
}
