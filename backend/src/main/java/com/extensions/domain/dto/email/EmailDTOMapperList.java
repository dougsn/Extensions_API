package com.extensions.domain.dto.email;

import com.extensions.domain.entity.Email;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class EmailDTOMapperList implements Function<List<Email>, List<EmailDTO>> {


    @Override
    public List<EmailDTO> apply(List<Email> emails) {
        List<EmailDTO> dtoList = new ArrayList<>();
        emails.forEach(email -> {
            EmailDTO dto = new EmailDTO(email.getId(), email.getConta(), email.getSenha(),
                    email.getSetor().getId(), email.getSetor().getNome());
            dtoList.add(dto);
        });
        return dtoList;

    }
}
