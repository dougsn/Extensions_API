package com.extensions.unittests.services;

import com.extensions.domain.dto.computador.ComputadorDTO;
import com.extensions.domain.dto.computador.ComputadorDTOMapper;
import com.extensions.domain.dto.computador.ComputadorDTOMapperList;
import com.extensions.domain.entity.Computador;
import com.extensions.repository.IComputadorRepository;
import com.extensions.repository.ISetorRepository;
import com.extensions.services.ComputadorService;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import com.extensions.unittests.mocks.MockComputador;
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
public class ComputadorServiceTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockComputador input;
    MockSetor inputSetor;

    @InjectMocks
    private ComputadorService service;
    @Mock
    IComputadorRepository repository;
    @Mock
    ISetorRepository setorRepository;
    @Mock
    ComputadorDTOMapper mapper;
    @Mock
    ComputadorDTOMapperList listMapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockComputador();
        inputSetor = new MockSetor();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindByHostname() {
        List<Computador> computadores = input.mockEntityList(inputSetor.mockEntity(UUID_MOCK));
        List<ComputadorDTO> dtoList = input.mockDTOList(inputSetor.mockDTO(UUID_MOCK).getId());

        when(repository.findComputadorByHostname(UUID_MOCK)).thenReturn(computadores);
        when(listMapper.apply(anyList())).thenReturn(dtoList);

        var computador = service.findComputadorByHostname(UUID_MOCK);
        assertNotNull(computador.get(0));
        assertEquals(10, computador.size());
    }


    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(mapper.apply(any(Computador.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getHostname());
        assertNotNull(result.getModelo());
        assertNotNull(result.getCpu());
        assertNotNull(result.getMemoria());
        assertNotNull(result.getDisco());
        assertNotNull(result.getSistemaOperacional());
        assertNotNull(result.getObservacao());
        assertNotNull(result.getIdSetor());

        assertTrue(result.toString().contains("links: [<http://localhost/api/computador/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("info_02", result.getHostname());
        assertEquals("Dell", result.getModelo());
        assertEquals("Core i5", result.getCpu());
        assertEquals("8GB RAM", result.getMemoria());
        assertEquals("SSD 240GB", result.getDisco());
        assertEquals("Windows 10", result.getSistemaOperacional());
        assertEquals("Computador de Douglas", result.getObservacao());
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
        when(repository.save(any(Computador.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE)));
        when(mapper.apply(any(Computador.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getHostname());
        assertNotNull(result.getModelo());
        assertNotNull(result.getCpu());
        assertNotNull(result.getMemoria());
        assertNotNull(result.getDisco());
        assertNotNull(result.getSistemaOperacional());
        assertNotNull(result.getObservacao());
        assertNotNull(result.getIdSetor());

        assertTrue(result.toString().contains("links: [<http://localhost/api/computador/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("info_02", result.getHostname());
        assertEquals("Dell", result.getModelo());
        assertEquals("Core i5", result.getCpu());
        assertEquals("8GB RAM", result.getMemoria());
        assertEquals("SSD 240GB", result.getDisco());
        assertEquals("Windows 10", result.getSistemaOperacional());
        assertEquals("Computador de Douglas", result.getObservacao());

    }

    @Test
    void testCreateWithTheSameName() {
        when(repository.findByHostname("info_02")).thenReturn(Optional.of(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE))));
        assertThrows(DataIntegratyViolationException.class, () -> service.checkingComputadorWithTheSameName(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId())));
    }


    @Test
    void testUpdate() {
        when(setorRepository.findById(UUID_MOCK)).thenReturn(Optional.of(inputSetor.mockEntity(UUID_MOCK)));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(repository.save(any(Computador.class))).thenReturn(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Computador.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.update(input.mockUpdateDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getHostname());
        assertNotNull(result.getModelo());
        assertNotNull(result.getCpu());
        assertNotNull(result.getMemoria());
        assertNotNull(result.getDisco());
        assertNotNull(result.getSistemaOperacional());
        assertNotNull(result.getObservacao());
        assertNotNull(result.getIdSetor());

        assertTrue(result.toString().contains("links: [<http://localhost/api/computador/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("info_02", result.getHostname());
        assertEquals("Dell", result.getModelo());
        assertEquals("Core i5", result.getCpu());
        assertEquals("8GB RAM", result.getMemoria());
        assertEquals("SSD 240GB", result.getDisco());
        assertEquals("Windows 10", result.getSistemaOperacional());
        assertEquals("Computador de Douglas", result.getObservacao());
    }

    @Test
    void testCheckingSectorWithTheSameNameDuringUpdate() {
        when(repository.findByHostname("info_02")).thenReturn(Optional.of(new Computador("1", "info_02", "Positivo",
                "Core i3", "4GB RAM", "SSD 120GB", "Windows 11", "Teste"
                , inputSetor.mockEntity(UUID_MOCK))));
        assertThrows(DataIntegratyViolationException.class, () -> service
                .checkingComputadorWithTheSameNameDuringUpdate(input.mockUpdateDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId())));
    }

    @Test
    void testDelete() {
        Computador Computador = input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(Computador));

        service.delete(UUID_MOCK);
    }


}
