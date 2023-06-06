package com.extensions.DTO.user;

import java.util.UUID;

public record UserDTO(
        Long id,
        String name,
        String email
) {
}
