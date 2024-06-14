package com.extensions.repository;

import com.extensions.domain.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITerminalRepository extends JpaRepository<Terminal, String> {
    @Transactional(readOnly = true)
    List<Terminal> findBySetorId(String idSetor);
}

