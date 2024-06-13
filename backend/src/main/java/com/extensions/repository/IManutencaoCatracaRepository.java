package com.extensions.repository;


import com.extensions.domain.entity.ManutencaoCatraca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IManutencaoCatracaRepository extends JpaRepository<ManutencaoCatraca, String> {
    @Transactional(readOnly = true)
    List<ManutencaoCatraca> findAllByDiaBetween(LocalDate dia, LocalDate fim);

    @Transactional(readOnly = true)
    List<ManutencaoCatraca> findByDefeitoLike(String defeito);

    @Transactional(readOnly = true)
    List<ManutencaoCatraca> findByCatracaId(String idCatraca);
}
