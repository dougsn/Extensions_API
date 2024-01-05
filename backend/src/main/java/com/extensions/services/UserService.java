package com.extensions.services;

import com.extensions.controller.SetorController;
import com.extensions.controller.UserController;
import com.extensions.domain.dto.user.UserDTO;
import com.extensions.domain.dto.user.UserDTOMapper;
import com.extensions.domain.dto.user.UserUpdateDTO;
import com.extensions.domain.dto.user.UserUpdateDTOMapper;
import com.extensions.domain.entity.User;
import com.extensions.repository.IUserRepository;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserDTOMapper mapper;
    private final UserUpdateDTOMapper updateMapper;
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findById(String id) {
        logger.info("Buscando setor de id: " + id);
        var user = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário de id: " + id + " não encontrado"));
        user.add(linkTo(methodOn(SetorController.class).findById(id)).withSelfRel());

        return user;
    }

    public UserDTO updateUser(UserUpdateDTO request) {
        userAlreadyRegistered(request);
        logger.info("Atualizando usuario");

        var userExisting = repository.findById(request.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado para ser atualizado."));

        var user = User.builder()
                .id(request.getId())
                .username(request.getName())
                .build();
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
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public void userAlreadyRegistered(UserUpdateDTO data) {
        Optional<User> userLogin = repository.findByUsername(data.getName());

        if (userLogin.isPresent() && !userLogin.get().getId().equals(data.getId()))
            throw new DataIntegratyViolationException("User already registered.");
    }

}