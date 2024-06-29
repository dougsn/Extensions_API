package com.extensions.controller;

import com.extensions.domain.dto.projeto.ProjetoDTO;
import com.extensions.domain.dto.projeto.ProjetoDTOSwagger;
import com.extensions.domain.dto.projeto.ProjetoUpdateDTO;
import com.extensions.services.ProjetoService;
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
@RequestMapping("/api/projeto/v1")
@Tag(description = "Projeto da aplicação", name = "Projeto")
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @Operation(summary = "Buscando todos os projetos", description = "Buscando todos os projetos",
            tags = {"Projeto"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProjetoDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<EntityModel<ProjetoDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "updatedAt"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Buscando todos os projetos por status", description = "Buscando todos os projetos por status",
            tags = {"Projeto"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProjetoDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/status/{idStatus}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProjetoDTO>> findProjetoByStatus(
            @PathVariable String idStatus
    ) {

        return ResponseEntity.ok(service.findByStatus(idStatus));
    }

    @Operation(summary = "Buscando todos os projetos por nome", description = "Buscando todos os projetos por nome",
            tags = {"Projeto"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProjetoDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/nome/{nome}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProjetoDTO>> findProjetoByNome(
            @PathVariable String nome
    ) {

        return ResponseEntity.ok(service.findByNome(nome));
    }

    @Operation(summary = "Buscar projeto pelo ID", description = "Buscar projeto pelo ID",
            tags = {"Projeto"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProjetoDTO.class)

                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjetoDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Criar um projeto", description = "Criar um projeto",
            tags = {"Projeto"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProjetoDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exemplo de payload para criar um projeto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjetoDTOSwagger.class)
                    )
            ))
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjetoDTO> add(@Valid @RequestBody ProjetoDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(data));
    }

    @Operation(summary = "Atualizar um projeto", description = "Atualizar um projeto",
            tags = {"Projeto"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProjetoDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exemplo de payload para atualizar um projeto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjetoDTOSwagger.class)
                    )
            ))
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjetoDTO> update(@Valid @RequestBody ProjetoUpdateDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(data));
    }

    @Operation(summary = "Deletar um projeto", description = "Deletar um projeto",
            tags = {"Projeto"},
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
