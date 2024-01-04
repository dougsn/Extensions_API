package com.extensions.controller;

import com.extensions.domain.dto.setor.SetorDTO;
import com.extensions.services.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/setor")
@Tag(description = "Setor da aplicação", name = "Setor")
public class SetorController {


    @Autowired
    private SetorService setorService;


    @Operation(summary = "Buscar todos os setores")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @GetMapping
    public ResponseEntity<List<SetorDTO>> findAll() {
        try {
            List<SetorDTO> list = setorService.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar tipo de setores pelo ID")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<SetorDTO>> findById(@PathVariable String id) {
        try {
            Optional<SetorDTO> setor = setorService.findById(id);

            if (setor.isPresent()) return ResponseEntity.ok(setor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @Operation(summary = "Criar um setor")
    @ApiResponse(responseCode = "201", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @PostMapping
    public ResponseEntity<Optional<SetorDTO>> add(@RequestBody SetorDTO setorDTO) {
        try {
            if (setorDTO != null) {
                Optional<SetorDTO> newSetor = setorService.add(setorDTO);
                if (newSetor.isPresent()) return new ResponseEntity<>(newSetor, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Atualizar um setor")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @PutMapping
    public ResponseEntity<Optional<SetorDTO>> update(@RequestBody SetorDTO setorDTO) {
        try {
            Optional<SetorDTO> setorUpdate = setorService.update(setorDTO);
            if (setorUpdate.isPresent()) return ResponseEntity.ok(setorUpdate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Deletar um setor")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<SetorDTO>> delete(@PathVariable String id) {
        try {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
