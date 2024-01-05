package com.extensions.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(type = "string", example = "John Doe")
    @NotBlank(message = "O campo [username] é obrigatório.")
    private String username;
    @Schema(type = "string", example = "#Password!")
    @NotBlank(message = "O campo [password] é obrigatório,")
    private String password;
}
