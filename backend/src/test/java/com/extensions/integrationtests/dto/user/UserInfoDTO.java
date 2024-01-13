
package com.extensions.integrationtests.dto.user;


import com.extensions.domain.entity.Permission;

import java.io.Serializable;
import java.util.List;

public class UserInfoDTO implements Serializable {
    private String id;
    private String name;
    private List<Permission> permissions;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String id, String name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
