package com.extensions.services;

import com.extensions.controller.UserController;
import com.extensions.domain.dto.auth.AuthenticationRequest;
import com.extensions.domain.dto.auth.AuthenticationResponse;
import com.extensions.domain.dto.auth.RegisterRequest;
import com.extensions.domain.dto.user.UserInfoDTO;
import com.extensions.domain.dto.user.UserInfoDTOMapper;
import com.extensions.domain.entity.User;
import com.extensions.infra.security.jwt.JwtService;
import com.extensions.repository.IUserRepository;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository repository;
    private final UserInfoDTOMapper userInfoDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        userAlreadyRegistered(request);

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Credenciais incorretas."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    @Transactional(readOnly = true)
    public UserInfoDTO infoUser() {
        User userEntity = repository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));

        return userInfoDTOMapper.apply(userEntity)
                .add(linkTo(methodOn(UserController.class).findById(userEntity.getId())).withSelfRel());
    }


    @Transactional(readOnly = true)
    public void userAlreadyRegistered(RegisterRequest data) {
        Optional<User> userLogin = repository.findByUsername(data.getUsername());

        if (userLogin.isPresent())
            throw new DataIntegratyViolationException("Usuário já registrado.");
    }


}