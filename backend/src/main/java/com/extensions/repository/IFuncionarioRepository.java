package com.extensions.repository;

import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, String> {
    @Transactional(readOnly = true)
    Page<Funcionario> findBySetorId(String idSetor, Pageable pageable);
    @Transactional(readOnly = true)
    List<Funcionario> findBySetor(Setor setor);
    @Transactional(readOnly = true)
    Optional<Funcionario> findByNome(String nome);
    @Query(value = "SELECT * FROM funcionarios f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))", nativeQuery = true)
    List<Funcionario> findFuncionarioByNome(@Param("nome") String nome);
}

