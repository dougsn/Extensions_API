package com.extensions.domain.dto.user;


import com.extensions.domain.entity.Role;

public record UserInfoDTO(
        Long id,
        String name,
        String email,
        Role role
) {
}
