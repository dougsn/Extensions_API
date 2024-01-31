package com.extensions.repository;


import com.extensions.domain.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IModeloRepository extends JpaRepository<Modelo, String> {
    @Transactional(readOnly = true)
    Optional<Modelo> findByNome(String nome);
}
