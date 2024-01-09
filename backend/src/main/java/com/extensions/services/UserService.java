package com.extensions.services;

import com.extensions.controller.UserController;
import com.extensions.domain.dto.user.UserDTO;
import com.extensions.domain.dto.user.UserDTOMapper;
import com.extensions.domain.dto.user.UserUpdateDTO;
import com.extensions.domain.entity.User;
import com.extensions.repository.IUserRepository;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserDTOMapper mapper;
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PagedResourcesAssembler<UserDTO> assembler;


    @Transactional(readOnly = true)
    public PagedModel<EntityModel<UserDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os usuários");

        var users = repository.findAll(pageable);

        var dtoList = users.map(u -> mapper.apply(u));
        dtoList.forEach(u -> u.add(linkTo(methodOn(UserController.class).findById(u.getId())).withSelfRel()));

        Link link = linkTo(methodOn(UserController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(String id) {
        logger.info("Buscando usuário de id: " + id);
        var user = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário de id: " + id + " não encontrado"));
        user.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());

        return user;
    }

    public UserDTO update(UserUpdateDTO request) {
        userAlreadyRegistered(request);
        logger.info("Atualizando usuario");

        var userExisting = repository.findById(request.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado para ser atualizado."));

        var user = new User(request.getId(), request.getName());
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            user.setPassword(userExisting.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getPermissions() == null || request.getPermissions().isEmpty()) {
            user.setPermissions(userExisting.getPermissions());
        } else {
            user.setPermissions(request.getPermissions());
        }


        return mapper.apply(repository.save(user))
                .add(linkTo(methodOn(UserController.class).findById(request.getId())).withSelfRel());
    }

    public Boolean delete(String id) {
        logger.info("Deletando usuario");
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Usuário de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void userAlreadyRegistered(UserUpdateDTO data) {
        Optional<User> userLogin = repository.findByUsername(data.getName());

        if (userLogin.isPresent() && !userLogin.get().getId().equals(data.getId()))
            throw new DataIntegratyViolationException("User already registered.");
    }

}