package com.extensions.integrationtests.repository;

import com.extensions.domain.entity.Funcionario;
import com.extensions.repository.IFuncionarioRepository;
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
public class FuncionarioRepositoryTest {

    @Autowired
    IFuncionarioRepository repository;

    @Autowired
    ISetorRepository setorRepository;
    private static Funcionario funcionario;

    @BeforeAll
    public static void setup() {
        funcionario = new Funcionario();
    }

    @Test
    public void testFindByNome(){
        var funcionarioByNome = repository.findByNome("Douglas Nascimento");

        assertNotNull(funcionarioByNome.get().getId());
        assertNotNull(funcionarioByNome.get().getNome());
        assertNotNull(funcionarioByNome.get().getEmail());
        assertNotNull(funcionarioByNome.get().getRamal());
        assertNotNull(funcionarioByNome.get().getSetor());


        assertEquals(funcionarioByNome.get().getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioByNome.get().getNome(), "Douglas Nascimento");
        assertEquals(funcionarioByNome.get().getRamal(), "123");
        assertEquals(funcionarioByNome.get().getEmail(), "douglas@gmail.com");
        assertEquals(funcionarioByNome.get().getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    @Test
    public void testListFindByNome(){
        var funcionarioByNome = repository.findFuncionarioByNome("Douglas Nascimento");

        assertNotNull(funcionarioByNome.get(0).getId());
        assertNotNull(funcionarioByNome.get(0).getNome());
        assertNotNull(funcionarioByNome.get(0).getEmail());
        assertNotNull(funcionarioByNome.get(0).getRamal());
        assertNotNull(funcionarioByNome.get(0).getSetor());


        assertEquals(funcionarioByNome.get(0).getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioByNome.get(0).getNome(), "Douglas Nascimento");
        assertEquals(funcionarioByNome.get(0).getRamal(), "123");
        assertEquals(funcionarioByNome.get(0).getEmail(), "douglas@gmail.com");
        assertEquals(funcionarioByNome.get(0).getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    @Test
    public void testFindBySetorId() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "nome"));

        var funcionariosBySetor =
                repository.findBySetorId("7bf808f8-da36-44ea-8fbd-79653a80023e",pageable);

        var list = funcionariosBySetor.stream().toList();
        var funcionarioOne = list.get(0);

        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getNome());
        assertNotNull(funcionarioOne.getEmail());
        assertNotNull(funcionarioOne.getRamal());
        assertNotNull(funcionarioOne.getSetor());

        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioOne.getNome(), "Douglas Nascimento");
        assertEquals(funcionarioOne.getRamal(), "123");
        assertEquals(funcionarioOne.getEmail(), "douglas@gmail.com");
        assertEquals(funcionarioOne.getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");

    }

    @Test
    public void testFindBySetor() {
        var setor = setorRepository.findById("7bf808f8-da36-44ea-8fbd-79653a80023e");

        var funcionariosBySetor =
                repository.findBySetor(setor.get());

        var list = funcionariosBySetor.stream().toList();
        var funcionarioOne = list.get(0);

        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getNome());
        assertNotNull(funcionarioOne.getEmail());
        assertNotNull(funcionarioOne.getRamal());
        assertNotNull(funcionarioOne.getSetor());

        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioOne.getNome(), "Douglas Nascimento");
        assertEquals(funcionarioOne.getRamal(), "123");
        assertEquals(funcionarioOne.getEmail(), "douglas@gmail.com");
        assertEquals(funcionarioOne.getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");

    }
}
