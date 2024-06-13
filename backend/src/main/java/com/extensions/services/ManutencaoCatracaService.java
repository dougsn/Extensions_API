package com.extensions.services;

import com.extensions.controller.ManutencaoCatracaController;
import com.extensions.domain.dto.manutencao_catraca.ManutencaoCatracaDTO;
import com.extensions.domain.dto.manutencao_catraca.ManutencaoCatracaDTOMapper;
import com.extensions.domain.entity.ManutencaoCatraca;
import com.extensions.repository.ICatracaRepository;
import com.extensions.repository.IManutencaoCatracaRepository;
import com.extensions.repository.IUserRepository;
import com.extensions.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ManutencaoCatracaService {
    private final Logger logger = Logger.getLogger(ManutencaoCatracaService.class.getName());
    @Autowired
    private IManutencaoCatracaRepository repository;
    @Autowired
    private ICatracaRepository catracaRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ManutencaoCatracaDTOMapper mapper;
    @Autowired
    PagedResourcesAssembler<ManutencaoCatracaDTO> assembler;

    @Transactional(readOnly = true)
    public List<ManutencaoCatracaDTO> findByDias(LocalDate inicio, LocalDate fim) {
        return repository.findAllByDiaBetween(inicio, fim).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ManutencaoCatracaDTO> findByDefeitoLike(String defeito) {
        return repository.findByDefeitoLike("%" + defeito + "%").stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ManutencaoCatracaDTO> findByIdCatraca(String catracaId) {
        return repository.findByCatracaId(catracaId).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ManutencaoCatracaDTO>> findByDiasAndCatraca(LocalDate inicio, LocalDate fim, String catracaId, Pageable pageable) {

        logger.info("Buscando todas as manutenções das catracas pelos dias: " + inicio + " " + fim);
        var manutencoes = repository.findAllByDiaBetweenAndCatracaId(inicio, fim, catracaId, pageable);
        var dtoList = manutencoes.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(ManutencaoCatracaController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ManutencaoCatracaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }


    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ManutencaoCatracaDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todas as manutenções das catracas!");
        var manutencaoCatracas = repository.findAll(pageable);

        var dtoList = manutencaoCatracas.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(ManutencaoCatracaController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ManutencaoCatracaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public ManutencaoCatracaDTO findById(String id) {
        logger.info("Buscando manuteção da catraca de id: " + id);
        var manutencaoCatraca = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Manutenção da catraca de id: " + id + " não encontrado"));
        manutencaoCatraca.add(linkTo(methodOn(ManutencaoCatracaController.class).findById(id)).withSelfRel());

        return manutencaoCatraca;
    }

    @Transactional
    public ManutencaoCatracaDTO add(ManutencaoCatracaDTO data) {
        logger.info("Adicionando uma nova manutenção de catraca.");
        var userEntity = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));

        var catraca = catracaRepository.findById(data.getIdCatraca())
                .orElseThrow(() -> new ObjectNotFoundException("Catraca de ID: " + data.getIdCatraca() + " não encontrado."));

        ManutencaoCatraca newManutencaoCatraca = repository.save(new ManutencaoCatraca(null, data.getDia(), data.getDefeito(),
                data.getObservacao(), catraca, userEntity.getUsername(), null, null));

        return mapper.apply(newManutencaoCatraca)
                .add(linkTo(methodOn(ManutencaoCatracaController.class).findById(newManutencaoCatraca.getId())).withSelfRel());
    }

    @Transactional
    public ManutencaoCatracaDTO update(ManutencaoCatracaDTO data) {
        logger.info("Atualizando catraca de id" + data.getId());
        var userEntity = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));

        var catraca = catracaRepository.findById(data.getIdCatraca())
                .orElseThrow(() -> new ObjectNotFoundException("Catraca de ID: " + data.getIdCatraca() + " não encontrado."));
        var manutencaoCatraca = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Manutenção da catraca de ID: " + data.getId() + " não encontrado."));

        ManutencaoCatraca newManutencaoCatraca = new ManutencaoCatraca(data.getId(), data.getDia(), data.getDefeito(),
                data.getObservacao(), catraca, manutencaoCatraca.getCreated_by(), LocalDateTime.now(), userEntity.getUsername());

        return mapper.apply(repository.save(newManutencaoCatraca))
                .add(linkTo(methodOn(ManutencaoCatracaController.class).findById(data.getId())).withSelfRel());

    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando manutenção da catraca de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Manutenção da catraca de id: " + id + " não encontrado.");
    }

}
