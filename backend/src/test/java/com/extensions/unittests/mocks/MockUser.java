package com.extensions.unittests.mocks;

import com.extensions.domain.dto.user.UserDTO;
import com.extensions.domain.dto.user.UserUpdateDTO;
import com.extensions.domain.entity.Permission;
import com.extensions.domain.entity.User;

import java.util.Collections;

public class MockUser {

    public User mockEntityAdmin(String id) {
        User user = new User();
        user.setId(id);
        user.setUsername("Teste");
        user.setPassword("admin123");
        user.setPermissions(Collections.singletonList(adminPermission()));
        return user;
    }

    public UserDTO mockDTOAdmin(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setName("Teste");
        dto.setPermissions(Collections.singletonList(adminPermission()));
        return dto;
    }

    public User mockEntity(String id) {
        User user = new User();
        user.setId(id);
        user.setUsername("Teste");
        return user;
    }

    public UserDTO mockDTO(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setName("Teste");
        dto.setPermissions(Collections.singletonList(adminPermission()));
        return dto;
    }

    public UserDTO mockDTOUpdated(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setName("Teste Modificado");
        dto.setPermissions(Collections.singletonList(managerPermission()));
        return dto;
    }

    public Permission adminPermission() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setDescription("ADMIN");
        return permission;
    }

    public Permission managerPermission() {
        Permission permission = new Permission();
        permission.setId(2L);
        permission.setDescription("MANAGER");
        return permission;
    }

}
