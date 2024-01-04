package com.extensions.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "funcionarios")
@Table(name="funcionarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Funcionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String ramal;
    String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_setores")
    Setor setor;

}
