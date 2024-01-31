package com.extensions.repository;


import com.extensions.domain.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ILocalRepository extends JpaRepository<Local, String> {
    @Transactional(readOnly = true)
    Optional<Local> findByNome(String nome);
}
