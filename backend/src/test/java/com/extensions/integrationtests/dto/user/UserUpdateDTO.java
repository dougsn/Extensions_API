package com.extensions.integrationtests.dto.user;


import com.extensions.domain.entity.Permission;

import java.io.Serializable;
import java.util.List;

public class UserUpdateDTO implements Serializable {
    private String id;
    private String name;
    private String password;
    private List<Permission> permissions;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String id, String name, String password, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
