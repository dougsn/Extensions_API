package com.extensions.repository;


import com.extensions.domain.entity.Setor;
import com.extensions.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IStatusRepository extends JpaRepository<Status, String> {
    @Transactional(readOnly = true)
    Optional<Setor> findByNome(String nome);
}
