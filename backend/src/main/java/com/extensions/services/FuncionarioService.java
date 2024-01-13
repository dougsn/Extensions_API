package com.extensions.services;

import com.extensions.controller.FuncionarioController;
import com.extensions.domain.dto.funcionario.FuncionarioDTO;
import com.extensions.domain.dto.funcionario.FuncionarioDTOMapper;
import com.extensions.domain.dto.funcionario.FuncionarioUpdateDTO;
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

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FuncionarioService {
    private final Logger logger = Logger.getLogger(FuncionarioService.class.getName());
    @Autowired
    private FuncionarioDTOMapper mapper;
    @Autowired
    private IFuncionarioRepository repository;
    @Autowired
    private ISetorRepository setorRepository;
    @Autowired
    PagedResourcesAssembler<FuncionarioDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<FuncionarioDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os funcionários!");
        var funcionarios = repository.findAll(pageable);

        var dtoList = funcionarios.map(f -> mapper.apply(f));
        dtoList.forEach(f -> f.add(linkTo(methodOn(FuncionarioController.class).findById(f.getId())).withSelfRel()));

        Link link = linkTo(methodOn(FuncionarioController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<FuncionarioDTO>> findFuncionarioBySetor(Pageable pageable, String idSetor) {
        logger.info("Buscando funcionário do setor de ID: " + idSetor);

        var funcionarios = repository.findBySetorId(idSetor, pageable);

        var dtoList = funcionarios.map(f -> mapper.apply(f));
        dtoList.forEach(f -> f.add(linkTo(methodOn(FuncionarioController.class).findById(f.getId())).withSelfRel()));

        Link link = linkTo(methodOn(FuncionarioController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }


    public FuncionarioDTO findById(String id) {
        logger.info("Buscando funcionário de id: " + id);
        var funcionario = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Funcionário de id: " + id + " não encontrado"));
        funcionario.add(linkTo(methodOn(FuncionarioController.class).findById(id)).withSelfRel());

        return funcionario;
    }

    public FuncionarioDTO add(FuncionarioDTO data) {
        logger.info("Adicionando um novo funcionário.");
        checkingFuncionarioWithTheSameName(data);
        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Funcionario newFuncionario = repository.save(new Funcionario(null, data.getNome(), data.getRamal(), data.getEmail(),
                setor));

        return mapper.apply(newFuncionario)
                .add(linkTo(methodOn(FuncionarioController.class).findById(newFuncionario.getId())).withSelfRel());
    }

    @Transactional
    public FuncionarioDTO update(FuncionarioUpdateDTO data) {
        logger.info("Atualizando funcionário de id" + data.getId());
        checkingFuncionarioWithTheSameNameDuringUpdate(data);

        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));
        Funcionario updatedFuncionario = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Funcionário de id: " + data.getId() + " não encontrado."));

        updatedFuncionario.setNome(data.getNome());
        updatedFuncionario.setRamal(data.getRamal());
        updatedFuncionario.setEmail(data.getEmail());
        updatedFuncionario.setSetor(setor);

        return mapper.apply(repository.save(updatedFuncionario))
                .add(linkTo(methodOn(FuncionarioController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando funcionário de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Funcionário de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingFuncionarioWithTheSameNameDuringUpdate(FuncionarioUpdateDTO data) {
        var funcionario = repository.findByNome(data.getNome());
        if (funcionario.isPresent() && !funcionario.get().getId().equals(data.getId())) {
            logger.info("O funcionário com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O funcionário com de nome: " + data.getNome() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingFuncionarioWithTheSameName(FuncionarioDTO data) {
        if (repository.findByNome(data.getNome()).isPresent()) {
            logger.info("O funcionário com de nome: " + data.getNome() + " já existe!");
            throw new DataIntegratyViolationException("O funcionário com de nome: " + data.getNome() + " já existe!");
        }

    }

}
