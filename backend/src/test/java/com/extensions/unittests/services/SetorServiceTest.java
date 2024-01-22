package com.extensions.unittests.services;

import com.extensions.domain.dto.setor.SetorDTO;
import com.extensions.domain.dto.setor.SetorDTOMapper;
import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.IFuncionarioRepository;
import com.extensions.repository.ISetorRepository;
import com.extensions.services.SetorService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class SetorServiceTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockSetor input;
    MockFuncionario inputFuncionario;

    @InjectMocks
    private SetorService service;
    @Mock
    ISetorRepository repository;
    @Mock
    IFuncionarioRepository funcionarioRepository;
    @Mock
    SetorDTOMapper mapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockSetor();
        inputFuncionario = new MockFuncionario();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Setor.class))).thenReturn(input.mockDTO(UUID_MOCK));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.toString().contains("links: [<http://localhost/api/setor/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("TI " + UUID_MOCK, result.getNome());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.findById("7bf808f8-da36-44ea-8fbd-asdasd312das"));
    }

    @Test
    void testCreate() {
        when(repository.save(any(Setor.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE));
        when(mapper.apply(any(Setor.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.toString().contains("links: [<http://localhost/api/setor/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("TI " + UUID_MOCK_CREATE, result.getNome());
    }

    @Test
    void testCreateWithTheSameName() {
        when(repository.findByNome("TI 7bf807f7-da36-55ae-0dqw-95210a80066a")).thenReturn(Optional.of(input.mockEntity(UUID_MOCK_CREATE)));
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingSectorWithTheSameName(input.mockDTO(UUID_MOCK_CREATE)));
    }

    @Test
    void testUpdate() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(repository.save(any(Setor.class))).thenReturn(input.mockEntity(UUID_MOCK));
        when(mapper.apply(any(Setor.class))).thenReturn(input.mockDTO(UUID_MOCK));

        var result = service.update(input.mockDTO(UUID_MOCK));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.toString().contains("links: [<http://localhost/api/setor/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("TI " + UUID_MOCK, result.getNome());
    }

    @Test
    void testCheckingSectorWithTheSameNameDuringUpdate() {
        when(repository.findByNome("NomeExistente")).thenReturn(Optional.of(new Setor("1", "NomeExistente")));
        SetorDTO data = new SetorDTO("2", "NomeExistente");
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingSectorWithTheSameNameDuringUpdate(data));
    }

    @Test
    void testDelete() {
        Setor setor = input.mockEntity(UUID_MOCK);
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(setor));

        service.delete(UUID_MOCK);
    }

    @Test
    void testDeleteWithRelationShip() {
        Setor setor = input.mockEntity(UUID_MOCK);
        List<Funcionario> funcList = new ArrayList<>();
        Funcionario funcionario = inputFuncionario.mockEntity(UUID_MOCK, setor);
        funcList.add(funcionario);
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(setor));
        when(funcionarioRepository.findBySetor(setor)).thenReturn(funcList);

        assertThrows(DataIntegratyViolationException.class, () -> service.delete(UUID_MOCK));
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.delete("3dasd1-da36-44ea-8fbd-asdasd312das"));
    }


}
