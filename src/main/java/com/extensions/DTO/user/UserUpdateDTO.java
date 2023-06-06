package com.extensions.DTO.user;


import com.extensions.entity.Role;


public record UserUpdateDTO(
        Long id,
        String name,
        String email,
        String password,
        Role role
) {
}
