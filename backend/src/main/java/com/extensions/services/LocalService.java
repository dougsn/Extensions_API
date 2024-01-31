package com.extensions.services;

import com.extensions.controller.LocalController;
import com.extensions.domain.dto.local.LocalDTO;
import com.extensions.domain.dto.local.LocalDTOMapper;
import com.extensions.domain.entity.Local;
import com.extensions.repository.ILocalRepository;
import com.extensions.services.exceptions.DataIntegratyViolationException;
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
public class LocalService {
    private final Logger logger = Logger.getLogger(LocalService.class.getName());
    @Autowired
    private ILocalRepository repository;
    @Autowired
    private LocalDTOMapper mapper;
    @Autowired
    PagedResourcesAssembler<LocalDTO> assembler;

    @Transactional(readOnly = true)
    public List<LocalDTO> findAllLocais() {
        return repository.findAll().stream().map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<LocalDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os locais!");
        var locais = repository.findAll(pageable);

        var dtoList = locais.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(LocalController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(LocalController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public LocalDTO findById(String id) {
        logger.info("Buscando local de id: " + id);
        var local = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Local de id: " + id + " não encontrado"));
        local.add(linkTo(methodOn(LocalController.class).findById(id)).withSelfRel());

        return local;
    }

    @Transactional
    public LocalDTO add(LocalDTO data) {
        logger.info("Adicionando um novo local.");
        checkingLocalWithTheSameName(data);

        Local newLocal = repository.save(new Local(null, data.getNome()));

        return mapper.apply(newLocal)
                .add(linkTo(methodOn(LocalController.class).findById(newLocal.getId())).withSelfRel());
    }

    @Transactional
    public LocalDTO update(LocalDTO data) {
        logger.info("Atualizando local de id" + data.getId());
        checkingLocalWithTheSameNameDuringUpdate(data);

        Local updateLocal = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Local de id: " + data.getId() + " não encontrado."));
        updateLocal.setNome(data.getNome());

        return mapper.apply(repository.save(updateLocal))
                .add(linkTo(methodOn(LocalController.class).findById(data.getId())).withSelfRel());

    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando local de id" + id);
        //validatingTheIntegrityOfTheRelationship(id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("local de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingLocalWithTheSameNameDuringUpdate(LocalDTO data) {
        var local = repository.findByNome(data.getNome());
        if (local.isPresent() && !local.get().getId().equals(data.getId())) {
            logger.info("O local com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O local com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingLocalWithTheSameName(LocalDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O local com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O local com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void validatingTheIntegrityOfTheRelationship(String id) {
        Local local = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Local ID: " + id + " not found."));

//        List<Funcionario> funcionarios = funcionarioRepository.findBySetor(local);
//
//        funcionarios.forEach(funcionario -> {
//            if (funcionario.getSetor().getId().equals(local.getId())) {
//                throw new DataIntegratyViolationException("O local está vinculado a um funcionário.");
//            }
//        });
    }
}
