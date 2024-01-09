package com.extensions.integrationtests.repository;

import com.extensions.domain.entity.Setor;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.repository.ISetorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SetorRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    ISetorRepository repository;
    private static Setor setor;

    @BeforeAll
    public static void setup() {
        setor = new Setor();
    }

    @Test
    public void testFindByNome(){
        var setorByName = repository.findByNome("TI");

        assertNotNull(setorByName.get().getNome());
        assertEquals(setorByName.get().getNome(), "TI");
    }
}
