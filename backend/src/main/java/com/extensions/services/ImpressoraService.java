package com.extensions.services;

import com.extensions.controller.ImpressoraController;
import com.extensions.domain.dto.impressora.ImpressoraDTO;
import com.extensions.domain.dto.impressora.ImpressoraDTOMapper;
import com.extensions.domain.dto.impressora.ImpressoraDTOMapperList;
import com.extensions.domain.dto.impressora.ImpressoraUpdateDTO;
import com.extensions.domain.entity.Impressora;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.IImpressoraRepository;
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
public class ImpressoraService {
    private final Logger logger = Logger.getLogger(ImpressoraService.class.getName());
    @Autowired
    private ImpressoraDTOMapper mapper;
    @Autowired
    private ImpressoraDTOMapperList listMapper;
    @Autowired
    private IImpressoraRepository repository;
    @Autowired
    private ISetorRepository setorRepository;
    @Autowired
    PagedResourcesAssembler<ImpressoraDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ImpressoraDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os Impressoras!");
        var impressoras = repository.findAll(pageable);

        var dtoList = impressoras.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(ImpressoraController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ImpressoraController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<ImpressoraDTO>> findImpressoraBySetor(Pageable pageable, String idSetor) {
        logger.info("Buscando impressora do setor de ID: " + idSetor);

        var impressoras = repository.findBySetorId(idSetor, pageable);

        var dtoList = impressoras.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(ImpressoraController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ImpressoraController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public List<ImpressoraDTO> findImpressoraByMarca(String marca) {
        logger.info("Buscando impressora de marca: " + marca);
        var impressoras = repository.findImpressoraByMarca(marca);

        var dtoList = listMapper.apply(impressoras);
        dtoList.forEach(e -> e.add(linkTo(methodOn(ImpressoraController.class).findById(e.getId())).withSelfRel()));

        return dtoList;
    }


    public ImpressoraDTO findById(String id) {
        logger.info("Buscando impressora de id: " + id);
        var impressora = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("impressora de id: " + id + " não encontrado"));
        impressora.add(linkTo(methodOn(ImpressoraController.class).findById(id)).withSelfRel());

        return impressora;
    }

    public ImpressoraDTO add(ImpressoraDTO data) {
        logger.info("Adicionando uma nova impressora.");
        checkingImpressoraWithTheSameIp(data);
        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Impressora newImpressora = repository.save(new Impressora(null, data.getMarca(), data.getModelo(), data.getIp(),
                data.getTonner(), data.getObservacao(), setor));

        return mapper.apply(newImpressora)
                .add(linkTo(methodOn(ImpressoraController.class).findById(newImpressora.getId())).withSelfRel());
    }

    @Transactional
    public ImpressoraDTO update(ImpressoraUpdateDTO data) {
        logger.info("Atualizando impressora de id: " + data.getId());
        checkingImpressoraWithTheSameIpDuringUpdate(data);

        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));
        Impressora updatedFuncionario = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Impressora de id: " + data.getId() + " não encontrado."));

        updatedFuncionario.setMarca(data.getMarca());
        updatedFuncionario.setModelo(data.getModelo());
        updatedFuncionario.setIp(data.getIp());
        updatedFuncionario.setTonner(data.getTonner());
        updatedFuncionario.setObservacao(data.getObservacao());
        updatedFuncionario.setSetor(setor);

        return mapper.apply(repository.save(updatedFuncionario))
                .add(linkTo(methodOn(ImpressoraController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando Impressora de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Impressora de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingImpressoraWithTheSameIpDuringUpdate(ImpressoraUpdateDTO data) {
        var impressora = repository.findByIp(data.getIp());
        if (impressora.isPresent() && !impressora.get().getId().equals(data.getId())) {
            logger.info("A impressora com o ip: " + data.getIp() + " já existe!");
            throw new DataIntegratyViolationException("A impressora com o ip: " + data.getIp() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingImpressoraWithTheSameIp(ImpressoraDTO data) {
        if (repository.findByIp(data.getIp()).isPresent()) {
            logger.info("A impressora com o ip: " + data.getIp() + " já existe!");
            throw new DataIntegratyViolationException("A impressora com o ip: " + data.getIp() + " já existe!");
        }

    }

}
