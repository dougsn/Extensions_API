package com.extensions.services;

import com.extensions.controller.AntenaController;
import com.extensions.domain.dto.antena.AntenaDTO;
import com.extensions.domain.dto.antena.AntenaDTOMapper;
import com.extensions.domain.dto.antena.AntenaDTOMapperList;
import com.extensions.domain.dto.antena.AntenaUpdateDTO;
import com.extensions.domain.entity.Antena;
import com.extensions.domain.entity.Local;
import com.extensions.domain.entity.Modelo;
import com.extensions.domain.entity.TipoAntena;
import com.extensions.repository.IAntenaRepository;
import com.extensions.repository.ILocalRepository;
import com.extensions.repository.IModeloRepository;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AntenaService {
    private final Logger logger = Logger.getLogger(AntenaService.class.getName());
    @Autowired
    private AntenaDTOMapper mapper;
    @Autowired
    private AntenaDTOMapperList listMapper;
    @Autowired
    private IAntenaRepository repository;
    @Autowired
    private ILocalRepository localRepository;
    @Autowired
    private IModeloRepository modeloRepository;
    @Autowired
    private ITipoAntenaRepository tipoAntenaRepository;
    @Autowired
    PagedResourcesAssembler<AntenaDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<AntenaDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos as antenas");
        var antenas = repository.findAll(pageable);

        var dtoList = antenas.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(AntenaController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(AntenaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<AntenaDTO>> findAntenaByLocal(Pageable pageable, String idLocal) {
        logger.info("Buscando antena do local de ID: " + idLocal);

        var antenas = repository.findByLocalId(idLocal, pageable);

        var dtoList = antenas.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(AntenaController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(AntenaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<AntenaDTO>> findAntenaByModelo(Pageable pageable, String idModelo) {
        logger.info("Buscando antena do modelo de ID: " + idModelo);

        var antenas = repository.findByModeloId(idModelo, pageable);

        var dtoList = antenas.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(AntenaController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(AntenaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<AntenaDTO>> findAntenaByTipoAntena(Pageable pageable, String idTipoAntena) {
        logger.info("Buscando antena do tipo de antena de ID: " + idTipoAntena);

        var antenas = repository.findByTipoAntenaId(idTipoAntena, pageable);

        var dtoList = antenas.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(AntenaController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(AntenaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public List<AntenaDTO> findAntenaBySsid(String ssid) {
        logger.info("Buscando antena de ssid: " + ssid);
        var antenas = repository.findAntenaBySsid(ssid);

        var dtoList = listMapper.apply(antenas);
        dtoList.forEach(e -> e.add(linkTo(methodOn(AntenaController.class).findById(e.getId())).withSelfRel()));

        return dtoList;
    }


    public AntenaDTO findById(String id) {
        logger.info("Buscando antena de id: " + id);
        var antena = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Antena de id: " + id + " não encontrado"));
        antena.add(linkTo(methodOn(AntenaController.class).findById(id)).withSelfRel());

        return antena;
    }

    public AntenaDTO add(AntenaDTO data) {
        logger.info("Adicionando um novo antena.");
        checkingAntenaWithTheSameIp(data);

        Local local = localRepository.findById(data.getIdLocal())
                .orElseThrow(() -> new ObjectNotFoundException("Local de ID: " + data.getIdLocal() + " não encontrado."));

        Modelo modelo = modeloRepository.findById(data.getIdModelo())
                .orElseThrow(() -> new ObjectNotFoundException("Modelo de ID: " + data.getIdModelo() + " não encontrado."));

        TipoAntena tipoAntena = tipoAntenaRepository.findById(data.getIdTipoAntena())
                .orElseThrow(() -> new ObjectNotFoundException("Tipo de antena de ID: " + data.getIdTipoAntena() + " não encontrado."));


        Antena newAntena = repository.save(new Antena(null, data.getIp(), data.getLocalizacao(), data.getSsid(),
                data.getSenha(), local, modelo, tipoAntena));

        return mapper.apply(newAntena)
                .add(linkTo(methodOn(AntenaController.class).findById(newAntena.getId())).withSelfRel());
    }

    @Transactional
    public AntenaDTO update(AntenaUpdateDTO data) {
        logger.info("Atualizando antena de id" + data.getId());
        checkingAntenaWithTheSameIpDuringUpdate(data);

        Local local = localRepository.findById(data.getIdLocal())
                .orElseThrow(() -> new ObjectNotFoundException("Local de ID: " + data.getIdLocal() + " não encontrado."));

        Modelo modelo = modeloRepository.findById(data.getIdModelo())
                .orElseThrow(() -> new ObjectNotFoundException("Modelo de ID: " + data.getIdModelo() + " não encontrado."));

        TipoAntena tipoAntena = tipoAntenaRepository.findById(data.getIdTipoAntena())
                .orElseThrow(() -> new ObjectNotFoundException("Tipo de antena de ID: " + data.getIdTipoAntena() + " não encontrado."));

        Antena updateAntena = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Antena de id: " + data.getId() + " não encontrado."));

        updateAntena.setIp(data.getIp());
        updateAntena.setLocalizacao(data.getLocalizacao());
        updateAntena.setSsid(data.getSsid());
        updateAntena.setSenha(data.getSenha());
        updateAntena.setLocal(local);
        updateAntena.setModelo(modelo);
        updateAntena.setTipoAntena(tipoAntena);

        return mapper.apply(repository.save(updateAntena))
                .add(linkTo(methodOn(AntenaController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando antena de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("antena de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingAntenaWithTheSameIpDuringUpdate(AntenaUpdateDTO data) {
        var antena = repository.findByIp(data.getIp());
        if (antena.isPresent() && !antena.get().getId().equals(data.getId())) {
            logger.info("A antena com de ip: " + data.getIp() + " já existe!");
            throw new DataIntegratyViolationException("A antena com de ip: " + data.getIp() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingAntenaWithTheSameIp(AntenaDTO data) {
        if (repository.findByIp(data.getIp()).isPresent()) {
            logger.info("A antena com de ip: " + data.getIp() + " já existe!");
            throw new DataIntegratyViolationException("A antena com de ip: " + data.getIp() + " já existe!");
        }

    }

}
