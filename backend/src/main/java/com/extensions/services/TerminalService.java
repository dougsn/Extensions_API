package com.extensions.services;

import com.extensions.controller.TerminalController;
import com.extensions.domain.dto.terminal.TerminalDTO;
import com.extensions.domain.dto.terminal.TerminalDTOMapper;
import com.extensions.domain.dto.terminal.TerminalUpdateDTO;
import com.extensions.domain.entity.Setor;
import com.extensions.domain.entity.Terminal;
import com.extensions.repository.ISetorRepository;
import com.extensions.repository.ITerminalRepository;
import com.extensions.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TerminalService {
    private final Logger logger = Logger.getLogger(TerminalService.class.getName());
    @Autowired
    private TerminalDTOMapper mapper;
    @Autowired
    private ITerminalRepository repository;
    @Autowired
    private ISetorRepository setorRepository;
    @Autowired
    PagedResourcesAssembler<TerminalDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<TerminalDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os terminais!");
        var terminals = repository.findAll(pageable);

        var dtoList = terminals.map(f -> mapper.apply(f));
        dtoList.forEach(f -> f.add(linkTo(methodOn(TerminalController.class).findById(f.getId())).withSelfRel()));

        Link link = linkTo(methodOn(TerminalController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public List<TerminalDTO> findWifiBySetor(String idSetor) {
        logger.info("Buscando terminal do setor de ID: " + idSetor);
        return repository.findBySetorId(idSetor).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    public TerminalDTO findById(String id) {
        logger.info("Buscando terminal de id: " + id);
        var terminal = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Terminal de id: " + id + " não encontrado"));
        terminal.add(linkTo(methodOn(TerminalController.class).findById(id)).withSelfRel());

        return terminal;
    }

    public TerminalDTO add(TerminalDTO data) {
        logger.info("Adicionando um novo terminal.");
        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Terminal newTerminal = repository.save(new Terminal(null, data.getUsuario(), data.getModelo(), setor));

        return mapper.apply(newTerminal)
                .add(linkTo(methodOn(TerminalController.class).findById(newTerminal.getId())).withSelfRel());
    }

    @Transactional
    public TerminalDTO update(TerminalUpdateDTO data) {
        logger.info("Atualizando terminal de id" + data.getId());

        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Terminal updatedTerminal = new Terminal(data.getId(), data.getUsuario(), data.getModelo(), setor);

        return mapper.apply(repository.save(updatedTerminal))
                .add(linkTo(methodOn(TerminalController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando terminal de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Terminal de id: " + id + " não encontrado.");
    }

}
