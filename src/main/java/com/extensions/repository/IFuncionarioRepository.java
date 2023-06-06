package com.extensions.repository;

import com.extensions.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByName(String name);
    Optional<Funcionario> findBySetor(String setor);
}
