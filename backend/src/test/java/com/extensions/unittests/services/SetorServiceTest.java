package com.extensions.unittests.services;

import com.extensions.config.testcontainers.AbstractIntegrationTest;
import com.extensions.domain.dto.setor.SetorDTOMapper;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.ISetorRepository;
import com.extensions.services.SetorService;
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
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static com.extensions.unittests.mocks.MockSetor.UUID_MOCK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class SetorServiceTest {
    MockSetor input;

    @InjectMocks
    private SetorService service;
    @Mock
    ISetorRepository repository;
    @Mock
    SetorDTOMapper mapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockSetor();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity()));
        when(mapper.apply(any(Setor.class))).thenReturn(input.mockDTO());

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());

        assertTrue(result.getLinks().toString().contains("<http://localhost/api/setor/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\""));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("TI", result.getNome());
    }






































}
