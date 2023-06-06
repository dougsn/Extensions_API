package com.extensions.DTO.funcionario;

import com.extensions.DTO.setor.SetorDTO;

public record FuncionarioDTO(
        Long id,
        String name,
        String ramal,
        String email,
        SetorDTO setorDTO
) {
}
