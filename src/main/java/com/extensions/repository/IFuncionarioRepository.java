package com.extensions.repository;

import com.extensions.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByName(String name);
    List<Funcionario> findBySetor(String setor);
}
