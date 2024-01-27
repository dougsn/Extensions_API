package com.extensions.integrationtests.repository;

import com.extensions.domain.entity.Impressora;
import com.extensions.repository.IImpressoraRepository;
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
public class ImpressoraRepositoryTest {

    @Autowired
    IImpressoraRepository repository;

    @Autowired
    ISetorRepository setorRepository;
    private static Impressora impressora;

    @BeforeAll
    public static void setup() {
        impressora = new Impressora();
    }

    @Test
    public void testFindByIp(){
        var impressoraByMarca = repository.findByIp("192.168.0.2");

        assertNotNull(impressoraByMarca.get().getId());
        assertNotNull(impressoraByMarca.get().getMarca());
        assertNotNull(impressoraByMarca.get().getModelo());
        assertNotNull(impressoraByMarca.get().getIp());
        assertNotNull(impressoraByMarca.get().getTonner());
        assertNotNull(impressoraByMarca.get().getObservacao());
        assertNotNull(impressoraByMarca.get().getSetor());


        assertEquals(impressoraByMarca.get().getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("HP", impressoraByMarca.get().getMarca());
        assertEquals("Tank", impressoraByMarca.get().getModelo());
        assertEquals("192.168.0.2", impressoraByMarca.get().getIp());
        assertEquals("Tonner HP", impressoraByMarca.get().getTonner());
        assertEquals("Impressora de Douglas", impressoraByMarca.get().getObservacao());
    }

    @Test
    public void testListFindByMarca(){
        var impressoraByMarca = repository.findImpressoraByMarca("HP");

        assertNotNull(impressoraByMarca.get(0).getId());
        assertNotNull(impressoraByMarca.get(0).getMarca());
        assertNotNull(impressoraByMarca.get(0).getModelo());
        assertNotNull(impressoraByMarca.get(0).getIp());
        assertNotNull(impressoraByMarca.get(0).getTonner());
        assertNotNull(impressoraByMarca.get(0).getObservacao());
        assertNotNull(impressoraByMarca.get(0).getSetor());


        assertEquals(impressoraByMarca.get(0).getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("HP", impressoraByMarca.get(0).getMarca());
        assertEquals("Tank", impressoraByMarca.get(0).getModelo());
        assertEquals("192.168.0.2", impressoraByMarca.get(0).getIp());
        assertEquals("Tonner HP", impressoraByMarca.get(0).getTonner());
        assertEquals("Impressora de Douglas", impressoraByMarca.get(0).getObservacao());
    }

    @Test
    public void testFindBySetorId() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "marca"));

        var funcionariosBySetor =
                repository.findBySetorId("7bf808f8-da36-44ea-8fbd-79653a80023e",pageable);

        var list = funcionariosBySetor.stream().toList();
        var impressoraByMarca = list.get(0);

        assertNotNull(impressoraByMarca.getId());
        assertNotNull(impressoraByMarca.getMarca());
        assertNotNull(impressoraByMarca.getModelo());
        assertNotNull(impressoraByMarca.getIp());
        assertNotNull(impressoraByMarca.getTonner());
        assertNotNull(impressoraByMarca.getObservacao());
        assertNotNull(impressoraByMarca.getSetor());


        assertEquals(impressoraByMarca.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("HP", impressoraByMarca.getMarca());
        assertEquals("Tank", impressoraByMarca.getModelo());
        assertEquals("192.168.0.2", impressoraByMarca.getIp());
        assertEquals("Tonner HP", impressoraByMarca.getTonner());
        assertEquals("Impressora de Douglas", impressoraByMarca.getObservacao());

    }

    @Test
    public void testFindBySetor() {
        var setor = setorRepository.findById("7bf808f8-da36-44ea-8fbd-79653a80023e");

        var funcionariosBySetor =
                repository.findBySetor(setor.get());

        var list = funcionariosBySetor.stream().toList();
        var impressoraOne = list.get(0);

        assertNotNull(impressoraOne.getId());
        assertNotNull(impressoraOne.getMarca());
        assertNotNull(impressoraOne.getModelo());
        assertNotNull(impressoraOne.getIp());
        assertNotNull(impressoraOne.getTonner());
        assertNotNull(impressoraOne.getObservacao());
        assertNotNull(impressoraOne.getSetor());


        assertEquals(impressoraOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals("HP", impressoraOne.getMarca());
        assertEquals("Tank", impressoraOne.getModelo());
        assertEquals("192.168.0.2", impressoraOne.getIp());
        assertEquals("Tonner HP", impressoraOne.getTonner());
        assertEquals("Impressora de Douglas", impressoraOne.getObservacao());

    }
}
