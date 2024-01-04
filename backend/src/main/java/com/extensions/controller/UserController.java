package com.extensions.controller;


import com.extensions.domain.dto.user.UserDTO;
import com.extensions.domain.dto.user.UserUpdateDTO;
import com.extensions.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") // Liberando o controlador dos CORS
@RequestMapping(value = "/user")
@Tag(description = "Usuários da aplicação", name = "Usuário")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Buscar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        try {
            List<UserDTO> list = userService.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar usuário pelo ID")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<UserDTO>> findById(@PathVariable Long id) throws Exception {
        try {
            Optional<UserDTO> user = userService.findById(id);

            if (user.isPresent()) return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @Operation(summary = "Atualizar usuário")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping
    public ResponseEntity<Optional<UserDTO>> updateUser(@RequestBody UserUpdateDTO user) {

        try {
            if (user != null) {
                Optional<UserDTO> updateUser = userService.updateUser(user);
                if (updateUser.isPresent()) {
                    return ResponseEntity.ok(updateUser);
                }
            }
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Deletar usuário")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> harDeleteUser(@PathVariable Long id) {
        try {
            if (userService.hardDeleteUser(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}