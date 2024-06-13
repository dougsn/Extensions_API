package com.extensions.controller;

import com.extensions.domain.dto.manutencao_catraca.ManutencaoCatracaDTO;
import com.extensions.domain.dto.manutencao_catraca.ManutencaoCatracaDTOSwagger;
import com.extensions.services.ManutencaoCatracaService;
import io.swagger.v3.oas.annotations.Operation;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/manutencao-catraca/v1")
@Tag(description = "Manutenção das Catracas da aplicação", name = "Manutenção Catraca")
public class ManutencaoCatracaController {

    @Autowired
    private ManutencaoCatracaService service;

    @Operation(summary = "Buscando todas as manutenções das manutenção das catracas", description = "Buscando todas as manutenções das manutenção das catracas",
            tags = {"Manutenção Catraca"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ManutencaoCatracaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<EntityModel<ManutencaoCatracaDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "dia"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Buscar manutenção das catracas pelo id da catraca", description = "Buscar manutenção das catracas pelo id da catraca",
            tags = {"Manutenção Catraca"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ManutencaoCatracaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "catraca/{idCatraca}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ManutencaoCatracaDTO>> findByIdCatraca(@PathVariable String idCatraca) {
        return ResponseEntity.ok().body(service.findByIdCatraca(idCatraca));
    }

    @Operation(summary = "Buscar manutenção das catracas pelo defeito", description = "Buscar manutenção das catracas pelo defeito",
            tags = {"Manutenção Catraca"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ManutencaoCatracaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/defeito/{defeito}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ManutencaoCatracaDTO>> findByDefeito(@PathVariable String defeito) {
        return ResponseEntity.ok().body(service.findByDefeitoLike(defeito));
    }

    @Operation(summary = "Buscar manutenção da catraca pelo intervalo de 2 dias. ")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ManutencaoCatracaDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/dias")
    public ResponseEntity<List<ManutencaoCatracaDTO>> findByDias(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return ResponseEntity.ok().body(service.findByDias(inicio, fim));
    }

    @Operation(summary = "Buscar manutenção da catraca pelo intervalo de 2 dias e pelo id da catraca. ")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ManutencaoCatracaDTO.class))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/dias-e-catraca")
    public ResponseEntity<PagedModel<EntityModel<ManutencaoCatracaDTO>>> findByDiasAndCatraca(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            @RequestParam String catracaId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "dia"));
        return ResponseEntity.ok().body(service.findByDiasAndCatraca(inicio, fim, catracaId, pageable));
    }

    @Operation(summary = "Buscar manutenção das catracas pelo ID", description = "Buscar manutenção das catracas pelo ID",
            tags = {"Manutenção Catraca"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ManutencaoCatracaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ManutencaoCatracaDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Criar uma manutenção das catracas", description = "Criar uma manutenção das catracas",
            tags = {"Manutenção Catraca"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ManutencaoCatracaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exemplo de payload para criar uma manutenção das catracas",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ManutencaoCatracaDTOSwagger.class)
                    )
            )
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ManutencaoCatracaDTO> add(@Valid @RequestBody ManutencaoCatracaDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(data));
    }

    @Operation(summary = "Atualizar uma manutenção das catracas", description = "Atualizar uma manutenção das catracas",
            tags = {"Manutenção Catraca"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ManutencaoCatracaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exemplo de payload para atualizar uma manutenção das catracas",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ManutencaoCatracaDTOSwagger.class)
                    )
            )
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ManutencaoCatracaDTO> update(@Valid @RequestBody ManutencaoCatracaDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(data));
    }

    @Operation(summary = "Deletar uma manutenção das catracas", description = "Deletar uma manutenção das catracas",
            tags = {"Manutenção Catraca"},
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
