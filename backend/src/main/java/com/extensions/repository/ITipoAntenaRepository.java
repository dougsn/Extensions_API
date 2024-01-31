package com.extensions.repository;


import com.extensions.domain.entity.TipoAntena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ITipoAntenaRepository extends JpaRepository<TipoAntena, String> {
    @Transactional(readOnly = true)
    Optional<TipoAntena> findByNome(String nome);
}
