package com.extensions.config;

import com.extensions.entity.Role;
import com.extensions.entity.User;
import com.extensions.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
            var user = User.builder()
                    .name("Administrador")
                    .email("administrador@extension.com")
                    .password(passwordEncoder.encode("extension"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
