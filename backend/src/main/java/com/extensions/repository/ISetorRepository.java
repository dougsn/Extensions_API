package com.extensions.repository;


import com.extensions.domain.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISetorRepository extends JpaRepository<Setor, String> {
    Optional<Setor> findByNome(String nome);
}
