package com.extensions.domain.dto.funcionario;

import com.extensions.domain.dto.setor.SetorDTO;

public record FuncionarioDTO(
        Long id,
        String name,
        String ramal,
        String email,
        SetorDTO setor
) {
}
