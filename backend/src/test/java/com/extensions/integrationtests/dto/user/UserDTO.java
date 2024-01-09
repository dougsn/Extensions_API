package com.extensions.integrationtests.dto.user;

import com.extensions.domain.entity.Permission;

import java.io.Serializable;
import java.util.List;


public class UserDTO implements Serializable {
    private String id;
    private String name;
    private List<Permission> permissions;

    public UserDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}