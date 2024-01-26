package com.extensions.services;

import com.extensions.controller.ComputadorController;
import com.extensions.domain.dto.computador.ComputadorDTO;
import com.extensions.domain.dto.computador.ComputadorDTOMapper;
import com.extensions.domain.dto.computador.ComputadorDTOMapperList;
import com.extensions.domain.dto.computador.ComputadorUpdateDTO;
import com.extensions.domain.entity.Computador;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.IComputadorRepository;
import com.extensions.repository.ISetorRepository;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ComputadorService {
    private final Logger logger = Logger.getLogger(ComputadorService.class.getName());
    @Autowired
    private ComputadorDTOMapper mapper;
    @Autowired
    private ComputadorDTOMapperList listMapper;
    @Autowired
    private IComputadorRepository repository;
    @Autowired
    private ISetorRepository setorRepository;
    @Autowired
    PagedResourcesAssembler<ComputadorDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ComputadorDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os computadores!");
        var computadores = repository.findAll(pageable);

        var dtoList = computadores.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(ComputadorController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ComputadorController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<ComputadorDTO>> findComputadorBySetor(Pageable pageable, String idSetor) {
        logger.info("Buscando computador do setor de ID: " + idSetor);

        var computadores = repository.findBySetorId(idSetor, pageable);

        var dtoList = computadores.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(ComputadorController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ComputadorController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public List<ComputadorDTO> findComputadorByHostname(String hostname) {
        logger.info("Buscando computador de nome: " + hostname);
        var computadores = repository.findComputadorByHostname(hostname);

        var dtoList = listMapper.apply(computadores);
        dtoList.forEach(e -> e.add(linkTo(methodOn(ComputadorController.class).findById(e.getId())).withSelfRel()));

        return dtoList;
    }


    public ComputadorDTO findById(String id) {
        logger.info("Buscando computador de id: " + id);
        var computador = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("computador de id: " + id + " não encontrado"));
        computador.add(linkTo(methodOn(ComputadorController.class).findById(id)).withSelfRel());

        return computador;
    }

    public ComputadorDTO add(ComputadorDTO data) {
        logger.info("Adicionando um novo computador.");
        checkingComputadorWithTheSameName(data);
        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Computador newComputador = repository.save(new Computador(null, data.getHostname(), data.getModelo(), data.getCpu(),
                data.getMemoria(), data.getDisco(), data.getSistemaOperacional(), data.getObservacao(), setor));

        return mapper.apply(newComputador)
                .add(linkTo(methodOn(ComputadorController.class).findById(newComputador.getId())).withSelfRel());
    }

    @Transactional
    public ComputadorDTO update(ComputadorUpdateDTO data) {
        logger.info("Atualizando computador de id" + data.getId());
        checkingComputadorWithTheSameNameDuringUpdate(data);

        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));
        Computador updatedFuncionario = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("computador de id: " + data.getId() + " não encontrado."));

        updatedFuncionario.setHostname(data.getHostname());
        updatedFuncionario.setModelo(data.getModelo());
        updatedFuncionario.setCpu(data.getCpu());
        updatedFuncionario.setMemoria(data.getMemoria());
        updatedFuncionario.setDisco(data.getDisco());
        updatedFuncionario.setSistemaOperacional(data.getSistemaOperacional());
        updatedFuncionario.setObservacao(data.getObservacao());
        updatedFuncionario.setSetor(setor);

        return mapper.apply(repository.save(updatedFuncionario))
                .add(linkTo(methodOn(ComputadorController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando computador de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Computador de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingComputadorWithTheSameNameDuringUpdate(ComputadorUpdateDTO data) {
        var computador = repository.findByHostname(data.getHostname());
        if (computador.isPresent() && !computador.get().getId().equals(data.getId())) {
            logger.info("O computador com de nome: " + data.getHostname() + " já existe!");
            throw new DataIntegratyViolationException("O computador com de nome: " + data.getHostname() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingComputadorWithTheSameName(ComputadorDTO data) {
        if (repository.findByHostname(data.getHostname()).isPresent()) {
            logger.info("O computador com de nome: " + data.getHostname() + " já existe!");
            throw new DataIntegratyViolationException("O computador com de nome: " + data.getHostname() + " já existe!");
        }

    }

}
