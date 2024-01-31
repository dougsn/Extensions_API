package com.extensions.services;

import com.extensions.controller.TipoAntenaController;
import com.extensions.domain.dto.tipo_antena.TipoAntenaDTO;
import com.extensions.domain.dto.tipo_antena.TipoAntenaDTOMapper;
import com.extensions.domain.entity.TipoAntena;
import com.extensions.repository.ITipoAntenaRepository;
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
public class TipoAntenaService {
    private final Logger logger = Logger.getLogger(TipoAntenaService.class.getName());
    @Autowired
    private ITipoAntenaRepository repository;
    @Autowired
    private TipoAntenaDTOMapper mapper;
    @Autowired
    PagedResourcesAssembler<TipoAntenaDTO> assembler;

    @Transactional(readOnly = true)
    public List<TipoAntenaDTO> findAllTipoAntena() {
        return repository.findAll().stream().map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<TipoAntenaDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os tipos de antena!");
        var tipoAntenas = repository.findAll(pageable);

        var dtoList = tipoAntenas.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(TipoAntenaController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(TipoAntenaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public TipoAntenaDTO findById(String id) {
        logger.info("Buscando tipo de antena de id: " + id);
        var TipoAntena = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Tipo de antena de id: " + id + " não encontrado"));
        TipoAntena.add(linkTo(methodOn(TipoAntenaController.class).findById(id)).withSelfRel());

        return TipoAntena;
    }

    @Transactional
    public TipoAntenaDTO add(TipoAntenaDTO data) {
        logger.info("Adicionando um novo tipo de antena.");
        checkingTipoAntenaWithTheSameName(data);

        TipoAntena newTipoAntena = repository.save(new TipoAntena(null, data.getNome()));

        return mapper.apply(newTipoAntena)
                .add(linkTo(methodOn(TipoAntenaController.class).findById(newTipoAntena.getId())).withSelfRel());
    }

    @Transactional
    public TipoAntenaDTO update(TipoAntenaDTO data) {
        logger.info("Atualizando tipo de antena de id" + data.getId());
        checkingTipoAntenaWithTheSameNameDuringUpdate(data);

        TipoAntena updateTipoAntena = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Tipo de antena de id: " + data.getId() + " não encontrado."));
        updateTipoAntena.setNome(data.getNome());

        return mapper.apply(repository.save(updateTipoAntena))
                .add(linkTo(methodOn(TipoAntenaController.class).findById(data.getId())).withSelfRel());

    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando tipo de antena de id" + id);
        //validatingTheIntegrityOfTheRelationship(id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("tipo de antena de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingTipoAntenaWithTheSameNameDuringUpdate(TipoAntenaDTO data) {
        var TipoAntena = repository.findByNome(data.getNome());
        if (TipoAntena.isPresent() && !TipoAntena.get().getId().equals(data.getId())) {
            logger.info("O tipo de antena com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O tipo de antena com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingTipoAntenaWithTheSameName(TipoAntenaDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O tipo de antena com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O tipo de antena com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void validatingTheIntegrityOfTheRelationship(String id) {
        TipoAntena tipoAntena = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("tipo de antena ID: " + id + " not found."));

//        List<Funcionario> funcionarios = funcionarioRepository.findBySetor(TipoAntena);
//
//        funcionarios.forEach(funcionario -> {
//            if (funcionario.getSetor().getId().equals(TipoAntena.getId())) {
//                throw new DataIntegratyViolationException("O TipoAntena está vinculado a um funcionário.");
//            }
//        });
    }
}
