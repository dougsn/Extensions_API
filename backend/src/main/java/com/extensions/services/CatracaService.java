package com.extensions.services;

import com.extensions.controller.CatracaController;
import com.extensions.domain.dto.catraca.CatracaDTO;
import com.extensions.domain.dto.catraca.CatracaDTOMapper;
import com.extensions.domain.entity.Catraca;
import com.extensions.repository.ICatracaRepository;
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
public class CatracaService {
    private final Logger logger = Logger.getLogger(CatracaService.class.getName());
    @Autowired
    private ICatracaRepository repository;
    @Autowired
    private CatracaDTOMapper mapper;
    @Autowired
    PagedResourcesAssembler<CatracaDTO> assembler;

    @Transactional(readOnly = true)
    public List<CatracaDTO> findAllLocais() {
        return repository.findAll().stream().map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<CatracaDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todas as catracas!");
        var locais = repository.findAll(pageable);

        var dtoList = locais.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(CatracaController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(CatracaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public CatracaDTO findById(String id) {
        logger.info("Buscando catraca de id: " + id);
        var catraca = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Catraca de id: " + id + " não encontrado"));
        catraca.add(linkTo(methodOn(CatracaController.class).findById(id)).withSelfRel());

        return catraca;
    }

    @Transactional
    public CatracaDTO add(CatracaDTO data) {
        logger.info("Adicionando uma nova catraca.");
        checkingCatracaWithTheSameName(data);

        Catraca newCatraca = repository.save(new Catraca(null, data.getNome(), data.getIp(), data.getNumeroDoEquipamento(),
                data.getNumeroDeSerie()));

        return mapper.apply(newCatraca)
                .add(linkTo(methodOn(CatracaController.class).findById(newCatraca.getId())).withSelfRel());
    }

    @Transactional
    public CatracaDTO update(CatracaDTO data) {
        logger.info("Atualizando catraca de id" + data.getId());
        checkingCatracaWithTheSameNameDuringUpdate(data);

        Catraca newCatraca = new Catraca(data.getId(), data.getNome(), data.getIp(), data.getNumeroDoEquipamento(),
                data.getNumeroDeSerie());

        return mapper.apply(repository.save(newCatraca))
                .add(linkTo(methodOn(CatracaController.class).findById(data.getId())).withSelfRel());

    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando catraca de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("catraca de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingCatracaWithTheSameNameDuringUpdate(CatracaDTO data) {
        var catraca = repository.findByNome(data.getNome());
        if (catraca.isPresent() && !catraca.get().getId().equals(data.getId())) {
            logger.info("A catraca com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O catraca com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingCatracaWithTheSameName(CatracaDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O catraca com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O catraca com de nome: " + data.getNome() + " já existe!");
        }
    }
}
