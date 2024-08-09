package com.extensions.repository;

import com.extensions.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IAntenaRepository extends JpaRepository<Antena, String> {
    @Transactional(readOnly = true)
    Page<Antena> findByLocalId(String idLocal, Pageable pageable);

    @Transactional(readOnly = true)
    List<Antena> findByLocal(Local local);

    @Transactional(readOnly = true)
    Page<Antena> findByModeloId(String idModelo, Pageable pageable);

    @Transactional(readOnly = true)
    List<Antena> findByModelo(Modelo modelo);


    @Transactional(readOnly = true)
    Page<Antena> findByTipoAntenaId(String idTipoAntena, Pageable pageable);

    @Transactional(readOnly = true)
    List<Antena> findByTipoAntena(TipoAntena tipoAntena);

    @Transactional(readOnly = true)
    Optional<Antena> findByIp(String ip);

    @Query(value = "SELECT * FROM antenas a WHERE LOWER(a.ssid) LIKE LOWER(CONCAT('%', :ssid, '%'))", nativeQuery = true)
    List<Antena> findAntenaBySsid(@Param("ssid") String ssid);

    @Query(value = "SELECT a.* FROM antenas a JOIN locais l ON a.fk_id_locais = l.id ORDER BY l.nome ASC", nativeQuery = true)
    List<Antena> findAllOrderByNomeLocal();
}

