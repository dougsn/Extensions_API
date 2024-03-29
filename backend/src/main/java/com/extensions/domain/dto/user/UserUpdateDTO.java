package com.extensions.domain.dto.user;


import com.extensions.domain.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

public class UserUpdateDTO extends RepresentationModel<UserUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    @NotBlank(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotBlank(message = "O campo [name] é obrigatório.")
    private String name;
    @Schema(type = "string", example = "!Password#")
    private String password;
    @Schema(type = "array", example = "[ADMIN, USER]")
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
