package com.extensions.DTO.user;


import com.extensions.entity.Role;

import java.util.UUID;

public record UserInfoDTO(
        Long id,
        String name,
        String email,
        Role role
) {
}
