package com.extensions.repository;

import com.extensions.domain.entity.Email;
import com.extensions.domain.entity.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IEmailRepository extends JpaRepository<Email, String> {
    @Transactional(readOnly = true)
    Page<Email> findBySetorId(String idSetor, Pageable pageable);

    @Transactional(readOnly = true)
    List<Email> findBySetor(Setor setor);

    @Transactional(readOnly = true)
    Optional<Email> findByConta(String nome);

    @Query(value = "SELECT * FROM caixa_email c WHERE LOWER(c.conta) LIKE LOWER(CONCAT('%', :conta, '%'))", nativeQuery = true)
    List<Email> findEmailByConta(@Param("conta") String conta);
}

