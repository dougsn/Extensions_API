package com.extensions.unittests.mocks;

import com.extensions.domain.dto.email.EmailDTO;
import com.extensions.domain.dto.email.EmailUpdateDTO;
import com.extensions.domain.entity.Email;
import com.extensions.domain.entity.Setor;

import java.util.ArrayList;
import java.util.List;


public class MockEmail {

    public List<Email> mockEntityList(Setor setor) {
        List<Email> emails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            emails.add(mockEntity("ID" + i, setor));
        }
        return emails;
    }

    public List<EmailDTO> mockDTOList(String idSetor) {
        List<EmailDTO> emails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            emails.add(mockDTO("ID" + i, idSetor));
        }
        return emails;
    }

    public Email mockEntity(String id, Setor setor) {
        Email Email = new Email();
        Email.setId(id);
        Email.setConta("douglas@gmail.com");
        Email.setSenha("123");
        Email.setSetor(setor);
        return Email;
    }

    public EmailDTO mockDTO(String id, String idSetor) {
        EmailDTO dto = new EmailDTO();
        dto.setId(id);
        dto.setConta("douglas@gmail.com");
        dto.setSenha("123");
        dto.setIdSetor(idSetor);
        return dto;
    }

    public EmailUpdateDTO mockUpdateDTO(String id, String idSetor) {
        EmailUpdateDTO dto = new EmailUpdateDTO();
        dto.setId(id);
        dto.setConta("douglas@gmail.com");
        dto.setSenha("123");
        dto.setIdSetor(idSetor);
        return dto;
    }
}
