package com.extensions.DTO.user;

import com.extensions.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserInfoDTOMapper implements Function<User, UserInfoDTO> {
    @Override
    public UserInfoDTO apply(User user) {
        return new UserInfoDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}