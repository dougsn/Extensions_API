package com.extensions.repository;

import com.extensions.domain.entity.Projeto;
import com.extensions.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProjetoRepository extends JpaRepository<Projeto, String> {
    @Transactional(readOnly = true)
    List<Projeto> findByStatusId(String statusId);

    @Transactional(readOnly = true)
    List<Projeto> findByNomeLike(String nome);

    @Transactional(readOnly = true)
    Optional<Projeto> findByNome(String nome);
}

