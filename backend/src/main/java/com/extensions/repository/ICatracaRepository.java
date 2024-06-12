package com.extensions.repository;


import com.extensions.domain.entity.Catraca;
import com.extensions.domain.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ICatracaRepository extends JpaRepository<Catraca, String> {
    @Transactional(readOnly = true)
    Optional<Catraca> findByNome(String nome);
    @Transactional(readOnly = true)
    Optional<Catraca> findByIp(String ip);
}
