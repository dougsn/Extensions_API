package com.extensions.unittests.services;

import com.extensions.domain.dto.impressora.ImpressoraDTO;
import com.extensions.domain.dto.impressora.ImpressoraDTOMapper;
import com.extensions.domain.dto.impressora.ImpressoraDTOMapperList;
import com.extensions.domain.entity.Impressora;
import com.extensions.repository.IImpressoraRepository;
import com.extensions.repository.ISetorRepository;
import com.extensions.services.ImpressoraService;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import com.extensions.unittests.mocks.MockImpressora;
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
public class ImpressoraServiceTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockImpressora input;
    MockSetor inputSetor;

    @InjectMocks
    private ImpressoraService service;
    @Mock
    IImpressoraRepository repository;
    @Mock
    ISetorRepository setorRepository;
    @Mock
    ImpressoraDTOMapper mapper;
    @Mock
    ImpressoraDTOMapperList listMapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockImpressora();
        inputSetor = new MockSetor();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindByMarca() {
        List<Impressora> impressoras = input.mockEntityList(inputSetor.mockEntity(UUID_MOCK));
        List<ImpressoraDTO> dtoList = input.mockDTOList(inputSetor.mockDTO(UUID_MOCK).getId());

        when(repository.findImpressoraByMarca(UUID_MOCK)).thenReturn(impressoras);
        when(listMapper.apply(anyList())).thenReturn(dtoList);

        var impressoraList = service.findImpressoraByMarca(UUID_MOCK);
        assertNotNull(impressoras.get(0));
        assertEquals(10, impressoraList.size());
    }


    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(mapper.apply(any(Impressora.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getMarca());
        assertNotNull(result.getModelo());
        assertNotNull(result.getIp());
        assertNotNull(result.getTonner());
        assertNotNull(result.getObservacao());
        assertNotNull(result.getIdSetor());

        assertTrue(result.toString().contains("links: [<http://localhost/api/impressora/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("HP", result.getMarca());
        assertEquals("Tank", result.getModelo());
        assertEquals("192", result.getIp());
        assertEquals("Tonner Tank", result.getTonner());
        assertEquals("Impressora de Douglas", result.getObservacao());
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
        when(repository.save(any(Impressora.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE)));
        when(mapper.apply(any(Impressora.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getMarca());
        assertNotNull(result.getModelo());
        assertNotNull(result.getIp());
        assertNotNull(result.getTonner());
        assertNotNull(result.getObservacao());
        assertNotNull(result.getIdSetor());

        assertTrue(result.toString().contains("links: [<http://localhost/api/impressora/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("HP", result.getMarca());
        assertEquals("Tank", result.getModelo());
        assertEquals("192", result.getIp());
        assertEquals("Tonner Tank", result.getTonner());
        assertEquals("Impressora de Douglas", result.getObservacao());

    }

    @Test
    void testCreateWithTheSameName() {
        when(repository.findByIp("192")).thenReturn(Optional.of(input.mockEntity(UUID_MOCK_CREATE, inputSetor.mockEntity(UUID_MOCK_CREATE))));
        assertThrows(DataIntegratyViolationException.class, () -> service
                .checkingImpressoraWithTheSameIp(input.mockDTO(UUID_MOCK_CREATE, inputSetor.mockDTO(UUID_MOCK_CREATE).getId())));
    }


    @Test
    void testUpdate() {
        when(setorRepository.findById(UUID_MOCK)).thenReturn(Optional.of(inputSetor.mockEntity(UUID_MOCK)));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK))));
        when(repository.save(any(Impressora.class))).thenReturn(input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Impressora.class))).thenReturn(input.mockDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));

        var result = service.update(input.mockUpdateDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId()));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getMarca());
        assertNotNull(result.getModelo());
        assertNotNull(result.getIp());
        assertNotNull(result.getTonner());
        assertNotNull(result.getObservacao());
        assertNotNull(result.getIdSetor());

        assertTrue(result.toString().contains("links: [<http://localhost/api/impressora/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("HP", result.getMarca());
        assertEquals("Tank", result.getModelo());
        assertEquals("192", result.getIp());
        assertEquals("Tonner Tank", result.getTonner());
        assertEquals("Impressora de Douglas", result.getObservacao());
    }

    @Test
    void testCheckingImpressoraWithTheSameNameDuringUpdate() {
        when(repository.findByIp("192")).thenReturn(Optional.of(new Impressora("1", "HP", "Tank", "192",
                "Teste", "Teste", inputSetor.mockEntity(UUID_MOCK))));

        assertThrows(DataIntegratyViolationException.class, () -> service
                .checkingImpressoraWithTheSameIpDuringUpdate(input.mockUpdateDTO(UUID_MOCK, inputSetor.mockDTO(UUID_MOCK).getId())));
    }

    @Test
    void testDelete() {
        Impressora Impressora = input.mockEntity(UUID_MOCK, inputSetor.mockEntity(UUID_MOCK));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(Impressora));

        service.delete(UUID_MOCK);
    }


}
