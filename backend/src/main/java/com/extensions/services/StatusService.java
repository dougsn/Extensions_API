package com.extensions.services;

import com.extensions.domain.dto.status.StatusDTO;
import com.extensions.domain.dto.status.StatusDTOMapper;
import com.extensions.repository.IStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StatusService {
    private final Logger logger = Logger.getLogger(StatusService.class.getName());
    @Autowired
    private IStatusRepository repository;
    @Autowired
    private StatusDTOMapper mapper;

    @Transactional(readOnly = true)
    public List<StatusDTO> findAllStatus() {
        return repository.findAll().stream().map(mapper)
                .collect(Collectors.toList());
    }
}
