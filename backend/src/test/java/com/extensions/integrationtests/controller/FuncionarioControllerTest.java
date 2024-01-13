package com.extensions.integrationtests.controller;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.funcionario.FuncionarioDTOTest;
import com.extensions.integrationtests.dto.funcionario.FuncionarioUpdateDTOTest;
import com.extensions.integrationtests.dto.setor.SetorDTO;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.integrationtests.wrappers.funcionario.WrapperFuncionarioDTO;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FuncionarioControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static FuncionarioDTOTest funcionario;
    private static FuncionarioUpdateDTOTest funcionarioUpdate;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        funcionario = new FuncionarioDTOTest();
        funcionarioUpdate = new FuncionarioUpdateDTOTest();
        setor = new SetorDTO();
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
        mockSetor();
        mockFuncionario();
        mockFuncionarioUpdate();
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
        funcionarioUpdate.setId(persistedFuncionario.getId());


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
        assertEquals(persistedFuncionario.getId(), funcionarioUpdate.getId());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException {
        funcionarioUpdate.setNome("Douglas Modificado");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(funcionarioUpdate)
                .when()
                .put()
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


        assertEquals("Douglas Modificado", persistedFuncionario.getNome());
        assertEquals("douglas@gmail.com", persistedFuncionario.getEmail());
        assertEquals("123", persistedFuncionario.getRamal());
        assertEquals(persistedFuncionario.getId_setor(), funcionario.getId_setor());
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


        assertEquals("Douglas Modificado", persistedFuncionario.getNome());
        assertEquals("douglas@gmail.com", persistedFuncionario.getEmail());
        assertEquals("123", persistedFuncionario.getRamal());
        assertEquals(persistedFuncionario.getId_setor(), funcionario.getId_setor());
    }

    @Test
    @Order(4)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", funcionario.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    public void testFindAll() throws JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 0, "size", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperFuncionarioDTO wrapper = objectMapper.readValue(content, WrapperFuncionarioDTO.class);
        var funcionarios = wrapper.getEmbeded().getFuncionarios();

        FuncionarioDTOTest funcionarioOne = funcionarios.get(0);

        assertNotNull(funcionarioOne);
        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getNome());
        assertNotNull(funcionarioOne.getEmail());
        assertNotNull(funcionarioOne.getRamal());
        assertNotNull(funcionarioOne.getId_setor());

        assertEquals(funcionarioOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");

        assertEquals("Douglas Nascimento", funcionarioOne.getNome());
        assertEquals("douglas@gmail.com", funcionarioOne.getEmail());
        assertEquals("123", funcionarioOne.getRamal());
        assertEquals(funcionarioOne.getId_setor(), "7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/funcionario/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(403);
    }

    @Test
    @Order(7)
    public void testHATEOAS() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 0, "size", 1, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        System.out.println(content);

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/funcionario/v1/1d3808f8-da36-44ea-8fbd-79653a80002s\"}}"));

        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/funcionario/v1?direction=asc&page=0&size=1&sort=nome,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/funcionario/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/funcionario/v1?direction=asc&page=1&size=1&sort=nome,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/funcionario/v1?direction=asc&page=1&size=1&sort=nome,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":2,\"totalPages\":2,\"number\":0}}"));
    }


    private void mockSetor() {
        setor.setId("7bf808f8-da36-44ea-8fbd-79653a80023e");
        setor.setNome("TI");
    }

    private void mockFuncionario() {
        funcionario.setNome("Douglas");
        funcionario.setRamal("123");
        funcionario.setEmail("douglas@gmail.com");
        funcionario.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    private void mockFuncionarioUpdate() {
        funcionarioUpdate.setNome("Douglas");
        funcionarioUpdate.setRamal("123");
        funcionarioUpdate.setEmail("douglas@gmail.com");
        funcionarioUpdate.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }
}
