package com.extensions.domain.dto.setor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTO extends RepresentationModel<SetorDTO> implements Serializable {
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotBlank(message = "O campo [nome] é obrigatório.")
    private String nome;
}
