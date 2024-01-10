package com.extensions.unittests.services;

import com.extensions.domain.dto.user.UserDTOMapper;
import com.extensions.domain.dto.user.UserUpdateDTO;
import com.extensions.domain.entity.User;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.repository.IUserRepository;
import com.extensions.services.UserService;
import com.extensions.services.exceptions.ObjectNotFoundException;
import com.extensions.unittests.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends AbstractIntegrationTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    MockUser input;
    @InjectMocks
    private UserService service;
    @Mock
    IUserRepository repository;
    @Mock
    UserDTOMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUpMocks() {
        input = new MockUser();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFinById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(User.class))).thenReturn(input.mockDTO(UUID_MOCK));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getPermissions());

        assertTrue(result.toString().contains("links: [<http://localhost/api/user/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Teste", result.getName());
        assertEquals("ADMIN", result.getPermissions().get(0).getDescription());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.findById("7bf808f8-da36-44ea-8fbd-asdasd312das"));
    }

    @Test
    void testUpdate() {
        UserUpdateDTO dto = new UserUpdateDTO(UUID_MOCK, "Teste Modificado", "manager1234",
                Collections.singletonList(input.managerPermission()));
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(repository.save(any(User.class))).thenReturn(input.mockEntity(UUID_MOCK));
        when(mapper.apply(any(User.class))).thenReturn(input.mockDTOUpdated(UUID_MOCK));

        var result = service.update(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getPermissions());

        assertTrue(result.toString().contains("links: [<http://localhost/api/user/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Teste Modificado", result.getName());
        assertEquals("MANAGER", result.getPermissions().get(0).getDescription());
    }

    @Test
    void testDelete() {
        User user = input.mockEntity(UUID_MOCK);
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(user));

        service.delete(UUID_MOCK);
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.delete("3dasd1-da36-44ea-8fbd-asdasd312das"));
    }


}
