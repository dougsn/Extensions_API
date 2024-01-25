package com.extensions.unittests.services;

import com.extensions.domain.dto.email.EmailDTO;
import com.extensions.domain.dto.email.EmailDTOMapper;
import com.extensions.domain.dto.email.EmailDTOMapperList;
import com.extensions.domain.dto.email.EmailUpdateDTO;
import com.extensions.domain.entity.Email;
import com.extensions.repository.IEmailRepository;
import com.extensions.repository.ISetorRepository;
import com.extensions.services.EmailService;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import com.extensions.unittests.mocks.MockEmail;
import com.extensions.unittests.mocks.MockSetor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockEmail input;
    MockSetor inputSetor;

    @InjectMocks
    private EmailService service;
    @Mock
    IEmailRepository repository;
    @Mock
    ISetorRepository setorRepository;
    @Mock
    EmailDTOMapper mapper;
    @Mock
    EmailDTOMapperList listMapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockEmail();
        inputSetor = new MockSetor();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindByConta() {
        List<Email> emails = input.mockEntityList(inputSetor.mockEntity(UUID_MOCK));
        List<EmailDTO> dtoList = input.mockDTOList(inputSetor.mockDTO(UUID_MOCK).getId());

        when(repository.findEmailByConta(UUID_MOCK)).thenReturn(emails);
        when(listMapper.apply(anyList())).thenReturn(dtoList);

        var email = service.findEmailByConta(UUID_MOCK);
        assertNotNull(email.get(0));
        assertEquals(10, email.size());
    }


    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(mapper.apply(any(Email.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getConta());
        assertNotNull(result.getSenha());

        assertTrue(result.toString().contains("links: [<http://localhost/api/email/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("douglas@gmail.com", result.getConta());
        assertEquals("123", result.getSenha());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.findById("7bf808f8-da36-44ea-8fbd-asdasd312das"));
    }


    @Test
    void testDeleteNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.delete("3dasd1-da36-44ea-8fbd-asdasd312das"));
    }

    @Test
    void testCreate() {
        when(setorRepository.findById(UUID_MOCK_CREATE)).thenReturn(Optional.of(inputSetor.mockEntity(UUID_MOCK_CREATE)));
        when(repository.save(any(Email.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE)));
        when(mapper.apply(any(Email.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getConta());
        assertNotNull(result.getSenha());

        assertTrue(result.toString().contains("links: [<http://localhost/api/email/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("douglas@gmail.com", result.getConta());
        assertEquals("123", result.getSenha());

    }

    @Test
    void testCreateWithTheSameName() {
        when(repository.findByConta("douglas@gmail.com")).thenReturn(Optional.of(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE))));
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingEmailWithTheSameName(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId())));
    }


    @Test
    void testUpdate() {
        when(setorRepository.findById(UUID_MOCK)).thenReturn(Optional.of(inputSetor.mockEntity(UUID_MOCK)));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(repository.save(any(Email.class))).thenReturn(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Email.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.update(input.mockUpdateDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getConta());
        assertNotNull(result.getSenha());

        assertTrue(result.toString().contains("links: [<http://localhost/api/email/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("douglas@gmail.com", result.getConta());
        assertEquals("123", result.getSenha());
    }

    @Test
    void testCheckingSectorWithTheSameNameDuringUpdate() {
        when(repository.findByConta("douglas@gmail.com")).thenReturn(Optional.of(new Email("1", "douglas@gmail.com", "123", inputSetor.mockEntity(UUID_MOCK))));
        EmailUpdateDTO data = new EmailUpdateDTO("123", "douglas@gmail.com", "123", UUID_MOCK);
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingEmailWithTheSameNameDuringUpdate(data));
    }

    @Test
    void testDelete() {
        Email Email = input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(Email));

        service.delete(UUID_MOCK);
    }


}
