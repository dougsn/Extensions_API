package com.extensions.domain.dto.email;

import com.extensions.domain.entity.Email;
import com.extensions.domain.entity.Funcionario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmailDTOMapper implements Function<Email, EmailDTO> {


    @Override
    public EmailDTO apply(Email email) {
        return new EmailDTO(
                email.getId(),
                email.getConta(),
                email.getSenha(),
                email.getSetor().getId(),
                email.getSetor().getNome()
        );

    }
}
