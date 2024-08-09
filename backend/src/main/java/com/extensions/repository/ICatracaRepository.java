package com.extensions.repository;


import com.extensions.domain.entity.Catraca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ICatracaRepository extends JpaRepository<Catraca, String> {
    @Transactional(readOnly = true)
    Optional<Catraca> findByNome(String nome);

    @Transactional(readOnly = true)
    Optional<Catraca> findByIp(String ip);

    @Query(value = "SELECT c.* FROM catracas c ORDER BY c.numero_do_equipamento ASC", nativeQuery = true)
    List<Catraca> findAllOrderByNomeCatraca();
}
