package com.extensions.repository;

import com.extensions.domain.entity.Computador;
import com.extensions.domain.entity.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IComputadorRepository extends JpaRepository<Computador, String> {
    @Transactional(readOnly = true)
    Page<Computador> findBySetorId(String idSetor, Pageable pageable);

    @Transactional(readOnly = true)
    List<Computador> findBySetor(Setor setor);

    @Transactional(readOnly = true)
    Optional<Computador> findByHostname(String hostname);

    @Query(value = "SELECT * FROM computadores c WHERE LOWER(c.hostname) LIKE LOWER(CONCAT('%', :hostname, '%'))", nativeQuery = true)
    List<Computador> findComputadorByHostname(@Param("hostname") String hostname);
}

