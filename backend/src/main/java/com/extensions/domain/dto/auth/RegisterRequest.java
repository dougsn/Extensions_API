package com.extensions.domain.dto.auth;

import com.extensions.domain.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public class RegisterRequest {
    @Schema(type = "string", example = "John Doe")
    @NotBlank(message = "O campo [username] é obrigatório.")
    private String username;
    @Schema(type = "string", example = "#Password!")
    @NotBlank(message = "O campo [password] é obrigatório,")
    private String password;
    @Schema(type = "array", example = "[ADMIN, USER]")
    private List<Permission> permissions;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, List<Permission> permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
