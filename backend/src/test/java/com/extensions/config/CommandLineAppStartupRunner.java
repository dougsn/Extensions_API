package com.extensions.config;

import com.extensions.domain.entity.User;
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
    public void run(String... args) {
        if (userRepository.findByUsername("Administrador").isEmpty()) {
            var user = User.builder()
                    .username("Administrador")
                    .password(passwordEncoder.encode("admin123"))
                    .build();
            userRepository.save(user);
        }
    }
}
