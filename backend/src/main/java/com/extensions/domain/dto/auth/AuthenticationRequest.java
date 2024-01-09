package com.extensions.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {
    @Schema(type = "string", example = "username")
    @NotBlank(message = "O campo [username] é obrigatório.")
    private String username;

    @Schema(type = "string", example = "#Password!")
    @NotBlank(message = "O campo [password] é obrigatório.")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
