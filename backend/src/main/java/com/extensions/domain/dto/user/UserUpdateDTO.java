package com.extensions.domain.dto.user;


import com.extensions.domain.entity.Role;


public record UserUpdateDTO(
        Long id,
        String name,
        String email,
        String password,
        Role role
) {
}
