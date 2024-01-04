package com.extensions.domain.dto.setor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTOSwagger implements Serializable {
    private String id;
    private String name;
}
