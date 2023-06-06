package com.extensions.repository;


import com.extensions.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISetorRepository extends JpaRepository<Setor, Long> {
    Optional<Setor> findByName(String name);
}
