package com.extensions.integrationtests.repository;

import com.extensions.domain.entity.Computador;
import com.extensions.repository.IComputadorRepository;
import com.extensions.repository.IEmailRepository;
import com.extensions.repository.ISetorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComputadorRepositoryTest {

    @Autowired
    IComputadorRepository repository;

    @Autowired
    ISetorRepository setorRepository;
    private static Computador computador;

    @BeforeAll
    public static void setup() {
        computador = new Computador();
    }

    @Test
    public void testFindByHostname(){
        var funcionarioByHostname = repository.findByHostname("info_02");

        assertNotNull(funcionarioByHostname.get().getId());
        assertNotNull(funcionarioByHostname.get().getHostname());
        assertNotNull(funcionarioByHostname.get().getModelo());
        assertNotNull(funcionarioByHostname.get().getCpu());
        assertNotNull(funcionarioByHostname.get().getMemoria());
        assertNotNull(funcionarioByHostname.get().getDisco());
        assertNotNull(funcionarioByHostname.get().getSistemaOperacional());
        assertNotNull(funcionarioByHostname.get().getObservacao());
        assertNotNull(funcionarioByHostname.get().getSetor());


        assertEquals(funcionarioByHostname.get().getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("info_02", funcionarioByHostname.get().getHostname());
        assertEquals("Dell", funcionarioByHostname.get().getModelo());
        assertEquals("Core i5", funcionarioByHostname.get().getCpu());
        assertEquals("8GB RAM", funcionarioByHostname.get().getMemoria());
        assertEquals("SSD 240GB", funcionarioByHostname.get().getDisco());
        assertEquals("Windows 10", funcionarioByHostname.get().getSistemaOperacional());
        assertEquals("Computador de Douglas", funcionarioByHostname.get().getObservacao());
    }

    @Test
    public void testListFindByNome(){
        var funcionarioByHostname = repository.findComputadorByHostname("info_02");

        assertNotNull(funcionarioByHostname.get(0).getId());
        assertNotNull(funcionarioByHostname.get(0).getHostname());
        assertNotNull(funcionarioByHostname.get(0).getModelo());
        assertNotNull(funcionarioByHostname.get(0).getCpu());
        assertNotNull(funcionarioByHostname.get(0).getMemoria());
        assertNotNull(funcionarioByHostname.get(0).getDisco());
        assertNotNull(funcionarioByHostname.get(0).getSistemaOperacional());
        assertNotNull(funcionarioByHostname.get(0).getObservacao());
        assertNotNull(funcionarioByHostname.get(0).getSetor());


        assertEquals(funcionarioByHostname.get(0).getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("info_02", funcionarioByHostname.get(0).getHostname());
        assertEquals("Dell", funcionarioByHostname.get(0).getModelo());
        assertEquals("Core i5", funcionarioByHostname.get(0).getCpu());
        assertEquals("8GB RAM", funcionarioByHostname.get(0).getMemoria());
        assertEquals("SSD 240GB", funcionarioByHostname.get(0).getDisco());
        assertEquals("Windows 10", funcionarioByHostname.get(0).getSistemaOperacional());
        assertEquals("Computador de Douglas", funcionarioByHostname.get(0).getObservacao());
    }

    @Test
    public void testFindBySetorId() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "hostname"));

        var funcionariosBySetor =
                repository.findBySetorId("7bf808f8-da36-44ea-8fbd-79653a80023e",pageable);

        var list = funcionariosBySetor.stream().toList();
        var funcionarioOne = list.get(0);

        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getHostname());
        assertNotNull(funcionarioOne.getModelo());
        assertNotNull(funcionarioOne.getCpu());
        assertNotNull(funcionarioOne.getMemoria());
        assertNotNull(funcionarioOne.getDisco());
        assertNotNull(funcionarioOne.getSistemaOperacional());
        assertNotNull(funcionarioOne.getObservacao());
        assertNotNull(funcionarioOne.getSetor());


        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("info_02", funcionarioOne.getHostname());
        assertEquals("Dell", funcionarioOne.getModelo());
        assertEquals("Core i5", funcionarioOne.getCpu());
        assertEquals("8GB RAM", funcionarioOne.getMemoria());
        assertEquals("SSD 240GB", funcionarioOne.getDisco());
        assertEquals("Windows 10", funcionarioOne.getSistemaOperacional());
        assertEquals("Computador de Douglas", funcionarioOne.getObservacao());

    }

    @Test
    public void testFindBySetor() {
        var setor = setorRepository.findById("7bf808f8-da36-44ea-8fbd-79653a80023e");

        var funcionariosBySetor =
                repository.findBySetor(setor.get());

        var list = funcionariosBySetor.stream().toList();
        var funcionarioOne = list.get(0);

        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getHostname());
        assertNotNull(funcionarioOne.getModelo());
        assertNotNull(funcionarioOne.getCpu());
        assertNotNull(funcionarioOne.getMemoria());
        assertNotNull(funcionarioOne.getDisco());
        assertNotNull(funcionarioOne.getSistemaOperacional());
        assertNotNull(funcionarioOne.getObservacao());
        assertNotNull(funcionarioOne.getSetor());


        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("info_02", funcionarioOne.getHostname());
        assertEquals("Dell", funcionarioOne.getModelo());
        assertEquals("Core i5", funcionarioOne.getCpu());
        assertEquals("8GB RAM", funcionarioOne.getMemoria());
        assertEquals("SSD 240GB", funcionarioOne.getDisco());
        assertEquals("Windows 10", funcionarioOne.getSistemaOperacional());
        assertEquals("Computador de Douglas", funcionarioOne.getObservacao());

    }
}
