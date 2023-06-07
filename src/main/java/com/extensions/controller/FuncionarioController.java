package com.extensions.controller;

import com.extensions.DTO.funcionario.FuncionarioDTO;
import com.extensions.DTO.setor.SetorDTO;
import com.extensions.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionario")
@CrossOrigin("*")
@Tag(description = "Funcionários da aplicação", name = "Funcionário")
public class FuncionarioController {
    final static Logger log = Logger.getLogger(String.valueOf(FuncionarioController.class));

    @Autowired
    private FuncionarioService service;

    @Operation(summary = "Buscar todos os funcionários")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        try {
            log.info("Buscando todos os funcionários");
            List<FuncionarioDTO> list = service.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e){
            log.error("Não foi possível buscar os funcionários");
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
            log.info("Buscando os funcionários pelo setor: " + setor);
            List<FuncionarioDTO> list = service.findFuncionarioBySetor(setor);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            log.error("Não foi possível buscar os funcionários do setor: " + setor);
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
            log.info("Buscando o funcionário de ID: " + id);
            Optional<FuncionarioDTO> funcionario = service.findById(id);

            if (funcionario.isPresent()) return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            log.error("Não foi possível buscar o funcionário de ID: " + id);
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @Operation(summary = "Criar um funcionário")
    @ApiResponse(responseCode = "201", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @PostMapping
    public ResponseEntity<Optional<FuncionarioDTO>> add (@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            log.info("Adicionando um novo funcionário");
            if (funcionarioDTO != null) {
                Optional<FuncionarioDTO> newFuncionario = service.add(funcionarioDTO);
                if (newFuncionario.isPresent()) return new ResponseEntity<>(newFuncionario, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("Não foi possível salvar o funcionário");
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @Operation(summary = "Atualizando um funcionário")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @PutMapping
    public ResponseEntity<Optional<FuncionarioDTO>> update (@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            log.info("Atualizando o funcionário de ID: " + funcionarioDTO.id());
            Optional<FuncionarioDTO> funcionarioUpdate = service.update(funcionarioDTO);
            if (funcionarioUpdate.isPresent()) return ResponseEntity.ok(funcionarioUpdate);
        } catch (Exception e) {
            log.error("Não foi possível atualizar o funcionário de ID: " + funcionarioDTO.id());
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @Operation(summary = "Deletando um funcionário")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<FuncionarioDTO>> delete (@PathVariable Long id) {
        try {
            log.info("Deletando o funcionário de ID: " + id);
            if (service.findById(id).isPresent() && service.hardDelete(id)) return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Não foi possível deletar o funcionário de ID: " + id);
            return ResponseEntity.notFound().build();
        }
        return null;
    }


}
