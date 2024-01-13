package com.extensions.integrationtests.controller.cors;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.funcionario.FuncionarioDTOTest;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FuncionarioControllerCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static FuncionarioDTOTest funcionario;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        funcionario = new FuncionarioDTOTest();
        authenticationResponse = new AuthenticationResponse();
    }


    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException {
        AuthenticationRequest user = new AuthenticationRequest("Administrator", "admin123");

        var token = given()
                .basePath("/api/auth/v1/login")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        authenticationResponse = objectMapper.readValue(token, AuthenticationResponse.class);
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARM_AUTHORIZATION, "Bearer " + authenticationResponse.getToken())
                .setBasePath("/api/funcionario/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockFuncionario();
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(funcionario)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        FuncionarioDTOTest persistedFuncionario = objectMapper.readValue(content, FuncionarioDTOTest.class);
        funcionario = persistedFuncionario;


        assertNotNull(persistedFuncionario);
        assertNotNull(persistedFuncionario.getId());
        assertNotNull(persistedFuncionario.getNome());
        assertNotNull(persistedFuncionario.getEmail());
        assertNotNull(persistedFuncionario.getRamal());
        assertNotNull(persistedFuncionario.getId_setor());

        assertEquals(persistedFuncionario.getId(), funcionario.getId());


        assertEquals("Douglas", persistedFuncionario.getNome());
        assertEquals("douglas@gmail.com", persistedFuncionario.getEmail());
        assertEquals("123", persistedFuncionario.getRamal());
    }

    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() {
        mockFuncionario();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .body(funcionario)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockFuncionario();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParams("id", funcionario.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        FuncionarioDTOTest persistedFuncionario = objectMapper.readValue(content, FuncionarioDTOTest.class);
        funcionario = persistedFuncionario;


        assertNotNull(persistedFuncionario);
        assertNotNull(persistedFuncionario.getId());
        assertNotNull(persistedFuncionario.getNome());
        assertNotNull(persistedFuncionario.getEmail());
        assertNotNull(persistedFuncionario.getRamal());
        assertNotNull(persistedFuncionario.getId_setor());

        assertEquals(persistedFuncionario.getId(), funcionario.getId());


        assertEquals("Douglas", persistedFuncionario.getNome());
        assertEquals("douglas@gmail.com", persistedFuncionario.getEmail());
        assertEquals("123", persistedFuncionario.getRamal());
    }

    @Test
    @Order(4)
    public void findByIdWithWrongOrigin() {
        mockFuncionario();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .pathParams("id", funcionario.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();


        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    private void mockFuncionario() {
        funcionario.setNome("Douglas");
        funcionario.setRamal("123");
        funcionario.setEmail("douglas@gmail.com");
        funcionario.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }
}
