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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user/v1")
@Tag(description = "Usuários da aplicação", name = "Usuário")
public class UserController {
    @Autowired
    private UserService service;

    @Operation(summary = "Search all users")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Search user by ID")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserUpdateDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO user) {
        return ResponseEntity.ok().body(service.updateUser(user));

    }

    @Operation(summary = "Delete user")
    @ApiResponse(responseCode = "204", description = "No Content", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> harDeleteUser(@PathVariable String id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}