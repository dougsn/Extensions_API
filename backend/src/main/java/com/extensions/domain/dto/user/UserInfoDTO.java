package com.extensions.domain.dto.user;


import com.extensions.domain.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

public class UserInfoDTO extends RepresentationModel<UserInfoDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotBlank(message = "O campo [name] é obrigatório.")
    private String name;
    @Schema(type = "array", example = "[{'id': 1}]")
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
