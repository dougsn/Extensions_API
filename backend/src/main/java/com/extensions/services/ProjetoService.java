package com.extensions.services;

import com.extensions.controller.ProjetoController;
import com.extensions.domain.dto.projeto.ProjetoDTO;
import com.extensions.domain.dto.projeto.ProjetoDTOMapper;
import com.extensions.domain.dto.projeto.ProjetoUpdateDTO;
import com.extensions.domain.entity.Projeto;
import com.extensions.domain.entity.User;
import com.extensions.repository.IProjetoRepository;
import com.extensions.repository.IStatusRepository;
import com.extensions.repository.IUserRepository;
import com.extensions.services.exceptions.DataIntegratyViolationException;
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
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProjetoService {
    private final Logger logger = Logger.getLogger(ProjetoService.class.getName());
    @Autowired
    private ProjetoDTOMapper mapper;
    @Autowired
    private IProjetoRepository repository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IStatusRepository statusRepository;
    @Autowired
    PagedResourcesAssembler<ProjetoDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ProjetoDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os projetos!");
        var projetos = repository.findAll(pageable);

        var dtoList = projetos.map(f -> mapper.apply(f));
        dtoList.forEach(f -> f.add(linkTo(methodOn(ProjetoController.class).findById(f.getId())).withSelfRel()));

        Link link = linkTo(methodOn(ProjetoController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public List<ProjetoDTO> findByStatus(String status) {
        logger.info("Buscando projeto de status: " + status);

        return repository.findByStatusId(status).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProjetoDTO> findByNome(String nome) {
        logger.info("Buscando projeto de nome: " + nome);
        return repository.findByNomeLike("%" + nome + "%").stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProjetoDTO findById(String id) {
        logger.info("Buscando projeto de id: " + id);
        var projeto = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Projeto de id: " + id + " não encontrado"));
        projeto.add(linkTo(methodOn(ProjetoController.class).findById(id)).withSelfRel());

        return projeto;
    }

    @Transactional
    public ProjetoDTO add(ProjetoDTO data) {
        logger.info("Adicionando um novo projeto.");
        checkingStatusWithTheSameName(data);
        User usuarioLogado = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
        var status = statusRepository.findById(data.getIdStatus())
                .orElseThrow(() -> new ObjectNotFoundException("Status de ID: " + data.getIdStatus() + " não encontrado."));

        Projeto newProjeto = repository.save(new Projeto(null, data.getNome(), data.getDescricao(),
                usuarioLogado.getUsername(), null, LocalDate.now(), null, status));

        return mapper.apply(newProjeto)
                .add(linkTo(methodOn(ProjetoController.class).findById(newProjeto.getId())).withSelfRel());
    }

    @Transactional
    public ProjetoDTO update(ProjetoUpdateDTO data) {
        logger.info("Atualizando projeto de id" + data.getId());
        checkingStatusWithTheSameNameDuringUpdate(data);
        User usuarioLogado = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
        var status = statusRepository.findById(data.getIdStatus())
                .orElseThrow(() -> new ObjectNotFoundException("Status de ID: " + data.getIdStatus() + " não encontrado."));
        var projetoExisting = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Projeto de ID: " + data.getId() + " não encontrado."));

        Projeto updatedProjeto = new Projeto(data.getId(), data.getNome(), data.getDescricao(),
                projetoExisting.getCreatedBy(), usuarioLogado.getUsername(), projetoExisting.getCreatedAt(), LocalDate.now(),
                status);

        return mapper.apply(repository.save(updatedProjeto))
                .add(linkTo(methodOn(ProjetoController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando projeto de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Projeto de id: " + id + " não encontrado.");
    }


    @Transactional(readOnly = true)
    public void checkingStatusWithTheSameNameDuringUpdate(ProjetoUpdateDTO data) {
        var projeto = repository.findByNome(data.getNome());
        if (projeto.isPresent() && !projeto.get().getId().equals(data.getId())) {
            logger.info("O projeto com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O projeto com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingStatusWithTheSameName(ProjetoDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O projeto com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O projeto com de nome: " + data.getNome() + " já existe!");
        }
    }


}
