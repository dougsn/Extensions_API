package com.extensions.repository;


import com.extensions.domain.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ISetorRepository extends JpaRepository<Setor, String> {
    @Transactional(readOnly = true)
    Optional<Setor> findByNome(String nome);
}
