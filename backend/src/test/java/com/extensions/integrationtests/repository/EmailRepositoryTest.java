package com.extensions.integrationtests.repository;

import com.extensions.domain.entity.Email;
import com.extensions.domain.entity.Funcionario;
import com.extensions.repository.IEmailRepository;
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
public class EmailRepositoryTest {

    @Autowired
    IEmailRepository repository;

    @Autowired
    ISetorRepository setorRepository;
    private static Email email;

    @BeforeAll
    public static void setup() {
        email = new Email();
    }

    @Test
    public void testFindByConta(){
        var funcionarioByNome = repository.findByConta("diogo@gmail.com");

        assertNotNull(funcionarioByNome.get().getId());
        assertNotNull(funcionarioByNome.get().getConta());
        assertNotNull(funcionarioByNome.get().getSetor());
        assertNotNull(funcionarioByNome.get().getSetor());


        assertEquals(funcionarioByNome.get().getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioByNome.get().getConta(), "diogo@gmail.com");
        assertEquals(funcionarioByNome.get().getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    @Test
    public void testListFindByNome(){
        var funcionarioByNome = repository.findEmailByConta("diogo@gmail.com");

        assertNotNull(funcionarioByNome.get(0).getId());
        assertNotNull(funcionarioByNome.get(0).getConta());
        assertNotNull(funcionarioByNome.get(0).getSetor());
        assertNotNull(funcionarioByNome.get(0).getSetor());


        assertEquals(funcionarioByNome.get(0).getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioByNome.get(0).getConta(), "diogo@gmail.com");
        assertEquals(funcionarioByNome.get(0).getSenha(), "123");
        assertEquals(funcionarioByNome.get(0).getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    @Test
    public void testFindBySetorId() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "conta"));

        var funcionariosBySetor =
                repository.findBySetorId("7bf808f8-da36-44ea-8fbd-79653a80023e",pageable);

        var list = funcionariosBySetor.stream().toList();
        var funcionarioOne = list.get(0);

        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getConta());
        assertNotNull(funcionarioOne.getSetor());
        assertNotNull(funcionarioOne.getSetor());

        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioOne.getConta(), "diogo@gmail.com");
        assertEquals(funcionarioOne.getSenha(), "123");
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
        assertNotNull(funcionarioOne.getConta());
        assertNotNull(funcionarioOne.getSetor());
        assertNotNull(funcionarioOne.getSetor());

        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");
        assertEquals(funcionarioOne.getConta(), "diogo@gmail.com");
        assertEquals(funcionarioOne.getSenha(), "123");
        assertEquals(funcionarioOne.getSetor().getId(), "7bf808f8-da36-44ea-8fbd-79653a80023e");

    }
}
