package com.extensions.domain.dto.email;

import com.extensions.domain.entity.Email;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmailDTOExportMapper implements Function<Email, EmailDTOExport> {


    @Override
    public EmailDTOExport apply(Email email) {
        return new EmailDTOExport(
                email.getSetor().getNome(),
                email.getConta(),
                email.getSenha()
        );

    }
}
