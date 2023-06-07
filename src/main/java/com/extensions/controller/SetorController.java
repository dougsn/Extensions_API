package com.extensions.controller;

import com.extensions.DTO.setor.SetorDTO;
import com.extensions.services.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
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

    final static Logger log = Logger.getLogger(String.valueOf(SetorController.class));

    @Autowired
    private SetorService setorService;


    @Operation(summary = "Buscar todos os setores")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @GetMapping
    public ResponseEntity<List<SetorDTO>> findAll() {
        try {
            log.info("Buscando todos os setores");
            List<SetorDTO> list = setorService.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            log.error("Nâo foi possível buscar os setores");
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar tipo de setores pelo ID")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<SetorDTO>> findById(@PathVariable Long id) {
        try {
            log.info("Buscando o tipo de equipamento pelo ID: " + id);
            Optional<SetorDTO> setor = setorService.findById(id);

            if (setor.isPresent()) return ResponseEntity.ok(setor);
        } catch (Exception e) {
            log.error("Não foi possível buscar os setores pelo ID: " + id);
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
            log.info("Adicionar um novo setor");
            if (setorDTO != null) {
                Optional<SetorDTO> newSetor = setorService.add(setorDTO);
                if (newSetor.isPresent()) return new ResponseEntity<>(newSetor, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("Não foi possível salvar o setor");
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Atualizar um setor")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @PutMapping
    public ResponseEntity<Optional<SetorDTO>> update (@RequestBody SetorDTO setorDTO) {
        try {
            log.info("Editando o setor de ID: " + setorDTO.id());
            Optional<SetorDTO> setorUpdate = setorService.update(setorDTO);
            if (setorUpdate.isPresent()) return ResponseEntity.ok(setorUpdate);
        } catch (Exception e) {
            log.error("Não foi possível editar o setor de ID: " + setorDTO.id());
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Deletar um setor")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SetorDTO.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<SetorDTO>> delete (@PathVariable Long id) {
        try {
            log.info("Deletando o setor de ID: " + id);
            if (setorService.findById(id).isPresent() && setorService.hardDelete(id)) return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Não foi possível deletar o setor de ID: " + id);
            return ResponseEntity.notFound().build();
        }
        return null;
    }


}
