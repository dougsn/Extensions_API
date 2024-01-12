package com.extensions.repository;

import com.extensions.domain.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, String> {
    @Transactional(readOnly = true)
    Page<Funcionario> findBySetorId(String idSetor, Pageable pageable);

    @Transactional(readOnly = true)
    Optional<Funcionario> findByNome(String nome);
}

