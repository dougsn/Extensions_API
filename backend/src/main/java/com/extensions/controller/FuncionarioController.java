package com.extensions.controller;

import com.extensions.domain.dto.funcionario.FuncionarioDTO;
import com.extensions.services.FuncionarioService;
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
@RequestMapping("/api/funcionario/v1")
@Tag(description = "Funcionários da aplicação", name = "Funcionário")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Operation(summary = "Buscar todos os funcionários")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        try {
            List<FuncionarioDTO> list = service.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar todos os funcionários por setor")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<FuncionarioDTO>> findFuncionarioBySetor(@PathVariable String setor) {
        try {
            List<FuncionarioDTO> list = service.findFuncionarioBySetor(setor);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar o funcionário pelo ID")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<FuncionarioDTO>> findById(@PathVariable Long id) {
        try {
            Optional<FuncionarioDTO> funcionario = service.findById(id);

            if (funcionario.isPresent()) return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @Operation(summary = "Criar um funcionário")
    @ApiResponse(responseCode = "201", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @PostMapping
    public ResponseEntity<Optional<FuncionarioDTO>> add(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            if (funcionarioDTO != null) {
                Optional<FuncionarioDTO> newFuncionario = service.add(funcionarioDTO);
                if (newFuncionario.isPresent()) return new ResponseEntity<>(newFuncionario, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Atualizando um funcionário")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @PutMapping
    public ResponseEntity<Optional<FuncionarioDTO>> update(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Optional<FuncionarioDTO> funcionarioUpdate = service.update(funcionarioDTO);
            if (funcionarioUpdate.isPresent()) return ResponseEntity.ok(funcionarioUpdate);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @Operation(summary = "Deletando um funcionário")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<FuncionarioDTO>> delete(@PathVariable Long id) {
        try {
            if (service.findById(id).isPresent() && service.hardDelete(id)) return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }


}
