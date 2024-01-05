package com.extensions.domain.dto.user;

import com.extensions.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserUpdateDTOMapper implements Function<User, UserUpdateDTO> {
    @Override
    public UserUpdateDTO apply(User user) {
        return new UserUpdateDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getPermissions()
        );
    }
}