package com.extensions.services;

import com.extensions.controller.SetorController;
import com.extensions.domain.dto.setor.SetorDTO;
import com.extensions.domain.dto.setor.SetorDTOMapper;
import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.IFuncionarioRepository;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SetorService {
    private final Logger logger = Logger.getLogger(SetorService.class.getName());
    @Autowired
    private IFuncionarioRepository funcionarioRepository;
    @Autowired
    private ISetorRepository repository;
    @Autowired
    private SetorDTOMapper mapper;
    @Autowired
    PagedResourcesAssembler<SetorDTO> assembler;

    @Transactional(readOnly = true)
    public List<SetorDTO> findAllSetores() {
        return repository.findAll().stream().map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<SetorDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os setores!");
        var setores = repository.findAll(pageable);

        var dtoList = setores.map(s -> mapper.apply(s));
        dtoList.forEach(s -> s.add(linkTo(methodOn(SetorController.class).findById(s.getId())).withSelfRel()));

        Link link = linkTo(methodOn(SetorController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public SetorDTO findById(String id) {
        logger.info("Buscando setor de id: " + id);
        var setor = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Setor de id: " + id + " não encontrado"));
        setor.add(linkTo(methodOn(SetorController.class).findById(id)).withSelfRel());

        return setor;
    }

    @Transactional
    public SetorDTO add(SetorDTO data) {
        logger.info("Adicionando um novo setor.");
        checkingSectorWithTheSameName(data);

        Setor newSetor = repository.save(new Setor(null, data.getNome()));

        return mapper.apply(newSetor)
                .add(linkTo(methodOn(SetorController.class).findById(newSetor.getId())).withSelfRel());
    }

    @Transactional
    public SetorDTO update(SetorDTO data) {
        logger.info("Atualizando setor de id" + data.getId());
        checkingSectorWithTheSameNameDuringUpdate(data);

        Setor updatedSetor = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de id: " + data.getId() + " não encontrado."));
        updatedSetor.setNome(data.getNome());

        return mapper.apply(repository.save(updatedSetor))
                .add(linkTo(methodOn(SetorController.class).findById(data.getId())).withSelfRel());

    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando setor de id" + id);
        validatingTheIntegrityOfTheRelationship(id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Setor de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingSectorWithTheSameNameDuringUpdate(SetorDTO data) {
        var setor = repository.findByNome(data.getNome());
        if (setor.isPresent() && !setor.get().getId().equals(data.getId())) {
            logger.info("O setor com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O setor com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingSectorWithTheSameName(SetorDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O setor com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O setor com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void validatingTheIntegrityOfTheRelationship(String id) {
        Setor setor = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Setor ID: " + id + " not found."));

        List<Funcionario> funcionarios = funcionarioRepository.findBySetor(setor);

        funcionarios.forEach(funcionario -> {
            if (funcionario.getSetor().getId().equals(setor.getId())) {
                throw new DataIntegratyViolationException("O setor está vinculado a um funcionário.");
            }
        });
    }
}
