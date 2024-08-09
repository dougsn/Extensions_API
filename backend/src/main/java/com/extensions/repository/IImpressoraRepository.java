package com.extensions.repository;

import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Impressora;
import com.extensions.domain.entity.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IImpressoraRepository extends JpaRepository<Impressora, String> {
    @Transactional(readOnly = true)
    Page<Impressora> findBySetorId(String idSetor, Pageable pageable);

    @Transactional(readOnly = true)
    List<Impressora> findBySetor(Setor setor);

    @Transactional(readOnly = true)
    Optional<Impressora> findByIp(String ip);

    @Query(value = "SELECT * FROM impressoras i WHERE LOWER(i.marca) LIKE LOWER(CONCAT('%', :marca, '%'))", nativeQuery = true)
    List<Impressora> findImpressoraByMarca(@Param("marca") String marca);

    @Query(value = "SELECT i.* FROM impressoras i JOIN setores s ON i.fk_id_setores = s.id ORDER BY s.nome ASC", nativeQuery = true)
    List<Impressora> findAllOrderByNomeSetor();
}

