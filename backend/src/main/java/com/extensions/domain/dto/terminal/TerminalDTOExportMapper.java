package com.extensions.domain.dto.terminal;

import com.extensions.domain.entity.Terminal;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TerminalDTOExportMapper implements Function<Terminal, TerminalDTOExport> {


    @Override
    public TerminalDTOExport apply(Terminal terminal) {
        return new TerminalDTOExport(
                terminal.getSetor().getNome(),
                terminal.getUsuario(),
                terminal.getModelo()
        );

    }
}
