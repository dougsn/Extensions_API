package com.extensions.services;

import com.extensions.domain.dto.user.UserInfoDTO;
import com.extensions.domain.dto.user.UserInfoDTOMapper;
import com.extensions.domain.dto.auth.AuthenticationRequest;
import com.extensions.domain.dto.auth.AuthenticationResponse;
import com.extensions.domain.dto.auth.RegisterRequest;
import com.extensions.infra.security.jwt.JwtService;
import com.extensions.domain.entity.Role;
import com.extensions.domain.entity.User;
import com.extensions.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository repository;
    private final UserInfoDTOMapper userInfoDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        var email = request.getEmail();
        if(repository.findByEmail(email).isPresent()) throw new Exception("E-mail já cadastrado");
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Optional<UserInfoDTO> infoUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
        String user = (String) principal;
        User userEntity = repository.findByEmail(user).get();

        return Optional.of(userInfoDTOMapper.apply(userEntity));
    }



}