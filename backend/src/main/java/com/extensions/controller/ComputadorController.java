package com.extensions.controller;

import com.extensions.domain.dto.computador.ComputadorDTO;
import com.extensions.domain.dto.computador.ComputadorDTOSwagger;
import com.extensions.domain.dto.computador.ComputadorUpdateDTO;
import com.extensions.services.ComputadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/computador/v1")
@Tag(description = "Computadores da aplicação", name = "Computadores")
public class ComputadorController {

    @Autowired
    private ComputadorService service;

    @Operation(summary = "Buscando todos os computadores", description = "Buscando todos os computadores",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ComputadorDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<EntityModel<ComputadorDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "hostname"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Buscando todos os computadores de um setor", description = "Buscando todos os computadores de um setor",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ComputadorDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/setor/{idSetor}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<EntityModel<ComputadorDTO>>> findComputadorBySetor(
            @PathVariable String idSetor,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "hostname"));
        return ResponseEntity.ok(service.findComputadorBySetor(pageable, idSetor));
    }

    @Operation(summary = "Buscar Computador pelo ID", description = "Buscar Computador pelo ID",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ComputadorDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ComputadorDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Buscar Computador pelo nome", description = "Buscar Computador pelo nome",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ComputadorDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/computador", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ComputadorDTO>> findComputadorByHostname(@RequestParam(value = "hostname", defaultValue = "") String hostname) {
        return ResponseEntity.ok().body(service.findComputadorByHostname(hostname));
    }

    @Operation(summary = "Criar um Computador", description = "Criar um Computador",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ComputadorDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exemplo de payload para criar um Computador",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComputadorDTOSwagger.class)
                    )
            )
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ComputadorDTO> add(@Valid @RequestBody ComputadorDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(data));
    }

    @Operation(summary = "Atualizar um Computador", description = "Atualizar um Computador",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ComputadorDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exemplo de payload para criar um Computador",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComputadorDTOSwagger.class)
                    )
            )
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ComputadorDTO> update(@Valid @RequestBody ComputadorUpdateDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(data));
    }

    @Operation(summary = "Deletar um Computador", description = "Deletar um Computador",
            tags = {"Computadores"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}
