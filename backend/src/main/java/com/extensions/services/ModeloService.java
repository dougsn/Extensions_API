package com.extensions.services;

import com.extensions.controller.ModeloController;
import com.extensions.domain.dto.modelo.ModeloDTO;
import com.extensions.domain.dto.modelo.ModeloDTOMapper;
import com.extensions.domain.entity.Antena;
import com.extensions.domain.entity.Modelo;
import com.extensions.repository.IAntenaRepository;
import com.extensions.repository.IModeloRepository;
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
public class ModeloService {
    private final Logger logger = Logger.getLogger(ModeloService.class.getName());
    @Autowired
    private IAntenaRepository antenaRepository;
    @Autowired
    private IModeloRepository repository;
    @Autowired
    private ModeloDTOMapper mapper;
    @Autowired
    PagedResourcesAssembler<ModeloDTO> assembler;

    @Transactional(readOnly = true)
    public List<ModeloDTO> findAllModelos() {
        return repository.findAll().stream().map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ModeloDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os modelos!");
        var modelos = repository.findAll(pageable);

        var dtoList = modelos.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(ModeloController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ModeloController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public ModeloDTO findById(String id) {
        logger.info("Buscando modelo de id: " + id);
        var modelo = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("modelo de id: " + id + " não encontrado"));
        modelo.add(linkTo(methodOn(ModeloController.class).findById(id)).withSelfRel());

        return modelo;
    }

    @Transactional
    public ModeloDTO add(ModeloDTO data) {
        logger.info("Adicionando um novo modelo.");
        checkingModeloWithTheSameName(data);

        Modelo newModelo = repository.save(new Modelo(null, data.getNome()));

        return mapper.apply(newModelo)
                .add(linkTo(methodOn(ModeloController.class).findById(newModelo.getId())).withSelfRel());
    }

    @Transactional
    public ModeloDTO update(ModeloDTO data) {
        logger.info("Atualizando modelo de id" + data.getId());
        checkingModeloWithTheSameNameDuringUpdate(data);

        Modelo updateLocal = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Modelo de id: " + data.getId() + " não encontrado."));
        updateLocal.setNome(data.getNome());

        return mapper.apply(repository.save(updateLocal))
                .add(linkTo(methodOn(ModeloController.class).findById(data.getId())).withSelfRel());

    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando modelo de id" + id);
        validatingTheIntegrityOfTheRelationship(id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Modelo de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingModeloWithTheSameNameDuringUpdate(ModeloDTO data) {
        var modelo = repository.findByNome(data.getNome());
        if (modelo.isPresent() && !modelo.get().getId().equals(data.getId())) {
            logger.info("O modelo com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O modelo com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingModeloWithTheSameName(ModeloDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O modelo com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O modelo com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void validatingTheIntegrityOfTheRelationship(String id) {
        Modelo modelo = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Modelo ID: " + id + " not found."));

        List<Antena> antenas = antenaRepository.findByModelo(modelo);

        antenas.forEach(antena -> {
            if (antena.getModelo().getId().equals(modelo.getId())) {
                throw new DataIntegratyViolationException("O modelo está vinculado a uma antena.");
            }
        });
    }
}
