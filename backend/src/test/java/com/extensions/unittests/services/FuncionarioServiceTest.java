package com.extensions.unittests.services;

import com.extensions.domain.dto.funcionario.FuncionarioDTOMapper;
import com.extensions.domain.dto.funcionario.FuncionarioUpdateDTO;
import com.extensions.domain.entity.Funcionario;
import com.extensions.repository.IFuncionarioRepository;
import com.extensions.repository.ISetorRepository;
import com.extensions.services.FuncionarioService;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import com.extensions.unittests.mocks.MockFuncionario;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockFuncionario input;
    MockSetor inputSetor;

    @InjectMocks
    private FuncionarioService service;
    @Mock
    IFuncionarioRepository repository;
    @Mock
    ISetorRepository setorRepository;
    @Mock
    FuncionarioDTOMapper mapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockFuncionario();
        inputSetor = new MockSetor();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(mapper.apply(any(Funcionario.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.toString().contains("links: [<http://localhost/api/funcionario/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Douglas " + UUID_MOCK, result.getNome());

        System.out.println(result);
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
        when(repository.save(any(Funcionario.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE)));
        when(mapper.apply(any(Funcionario.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.toString().contains("links: [<http://localhost/api/funcionario/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("Douglas " + UUID_MOCK_CREATE, result.getNome());

    }

    @Test
    void testCreateWithTheSameName() {
        when(repository.findByNome("Douglas 7bf807f7-da36-55ae-0dqw-95210a80066a")).thenReturn(Optional.of(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE))));
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingFuncionarioWithTheSameName(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId())));
    }


    @Test
    void testUpdate() {
        when(setorRepository.findById(UUID_MOCK)).thenReturn(Optional.of(inputSetor.mockEntity(UUID_MOCK)));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(repository.save(any(Funcionario.class))).thenReturn(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Funcionario.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.update(input.mockUpdateDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.toString().contains("links: [<http://localhost/api/funcionario/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Douglas " + UUID_MOCK, result.getNome());
    }
    @Test
    void testCheckingSectorWithTheSameNameDuringUpdate() {
        when(repository.findByNome("NomeExistente")).thenReturn(Optional.of(new Funcionario("1", "NomeExistente", "douglas@gmail.com", "123", inputSetor.mockEntity(UUID_MOCK))));
        FuncionarioUpdateDTO data = new FuncionarioUpdateDTO("123", "NomeExistente","123", "douglas@gmail.com", UUID_MOCK, "Administração");
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingFuncionarioWithTheSameNameDuringUpdate(data));
    }

    @Test
    void testDelete(){
        Funcionario funcionario = input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(funcionario));

        service.delete(UUID_MOCK);
    }


}
