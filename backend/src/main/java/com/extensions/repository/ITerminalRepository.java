package com.extensions.repository;

import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITerminalRepository extends JpaRepository<Terminal, String> {
    @Transactional(readOnly = true)
    List<Terminal> findBySetorId(String idSetor);
    @Query(value = "SELECT t.* FROM terminal t JOIN setores s ON t.fk_id_setores = s.id ORDER BY s.nome ASC", nativeQuery = true)
    List<Terminal> findAllOrderByNomeSetor();
}

