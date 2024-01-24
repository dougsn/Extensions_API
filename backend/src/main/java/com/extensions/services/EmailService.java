package com.extensions.services;

import com.extensions.controller.EmailController;
import com.extensions.domain.dto.email.EmailDTO;
import com.extensions.domain.dto.email.EmailDTOMapper;
import com.extensions.domain.dto.email.EmailDTOMapperList;
import com.extensions.domain.dto.email.EmailUpdateDTO;
import com.extensions.domain.entity.Email;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.IEmailRepository;
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
public class EmailService {
    private final Logger logger = Logger.getLogger(EmailService.class.getName());
    @Autowired
    private EmailDTOMapper mapper;
    @Autowired
    private EmailDTOMapperList listMapper;
    @Autowired
    private IEmailRepository repository;
    @Autowired
    private ISetorRepository setorRepository;
    @Autowired
    PagedResourcesAssembler<EmailDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<EmailDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os emails!");
        var emails = repository.findAll(pageable);

        var dtoList = emails.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(EmailController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(EmailController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public PagedModel<EntityModel<EmailDTO>> findEmailBySetor(Pageable pageable, String idSetor) {
        logger.info("Buscando email do setor de ID: " + idSetor);

        var emails = repository.findBySetorId(idSetor, pageable);

        var dtoList = emails.map(e -> mapper.apply(e));
        dtoList.forEach(e -> e.add(linkTo(methodOn(EmailController.class).findById(e.getId())).withSelfRel()));

        Link link = linkTo(methodOn(EmailController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public List<EmailDTO> findEmailByConta(String conta) {
        logger.info("Buscando email de nome: " + conta);
        var emails = repository.findEmailByConta(conta);

        var dtoList = listMapper.apply(emails);
        dtoList.forEach(e -> e.add(linkTo(methodOn(EmailController.class).findById(e.getId())).withSelfRel()));

        return dtoList;
    }


    public EmailDTO findById(String id) {
        logger.info("Buscando email de id: " + id);
        var Email = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("email de id: " + id + " não encontrado"));
        Email.add(linkTo(methodOn(EmailController.class).findById(id)).withSelfRel());

        return Email;
    }

    public EmailDTO add(EmailDTO data) {
        logger.info("Adicionando um novo email.");
        checkingEmailWithTheSameName(data);
        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Email newEmail = repository.save(new Email(null, data.getConta(), data.getSenha(), setor));

        return mapper.apply(newEmail)
                .add(linkTo(methodOn(EmailController.class).findById(newEmail.getId())).withSelfRel());
    }

    @Transactional
    public EmailDTO update(EmailUpdateDTO data) {
        logger.info("Atualizando email de id" + data.getId());
        checkingEmailWithTheSameNameDuringUpdate(data);

        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));
        Email updatedFuncionario = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("email de id: " + data.getId() + " não encontrado."));

        updatedFuncionario.setConta(data.getConta());
        updatedFuncionario.setSenha(data.getSenha());
        updatedFuncionario.setSetor(setor);

        return mapper.apply(repository.save(updatedFuncionario))
                .add(linkTo(methodOn(EmailController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando email de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("email de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingEmailWithTheSameNameDuringUpdate(EmailUpdateDTO data) {
        var Email = repository.findByConta(data.getConta());
        if (Email.isPresent() && !Email.get().getId().equals(data.getId())) {
            logger.info("O email com de nome: " + data.getConta() + " já existe!");
            throw new DataIntegratyViolationException("O email com de nome: " + data.getConta() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingEmailWithTheSameName(EmailDTO data) {
        if (repository.findByConta(data.getConta()).isPresent()) {
            logger.info("O email com de nome: " + data.getConta() + " já existe!");
            throw new DataIntegratyViolationException("O email com de nome: " + data.getConta() + " já existe!");
        }

    }

}
