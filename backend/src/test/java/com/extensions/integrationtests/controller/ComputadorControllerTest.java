package com.extensions.integrationtests.controller;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.computador.ComputadorDTOTest;
import com.extensions.integrationtests.dto.computador.ComputadorUpdateDTOTest;
import com.extensions.integrationtests.dto.setor.SetorDTO;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.integrationtests.wrappers.computador.WrapperComputadorDTO;
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
public class ComputadorControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static ComputadorDTOTest computador;
    private static ComputadorUpdateDTOTest computadorUpdate;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        computador = new ComputadorDTOTest();
        computadorUpdate = new ComputadorUpdateDTOTest();
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
                .setBasePath("/api/computador/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockSetor();
        mockComputador();
        mockComputadorUpdate();
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(computador)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        ComputadorDTOTest persistedComputador = objectMapper.readValue(content, ComputadorDTOTest.class);
        computador = persistedComputador;
        computadorUpdate.setId(persistedComputador.getId());


        assertNotNull(persistedComputador);
        assertNotNull(persistedComputador.getId());
        assertNotNull(persistedComputador.getHostname());
        assertNotNull(persistedComputador.getModelo());
        assertNotNull(persistedComputador.getCpu());
        assertNotNull(persistedComputador.getMemoria());
        assertNotNull(persistedComputador.getDisco());
        assertNotNull(persistedComputador.getSistemaOperacional());
        assertNotNull(persistedComputador.getObservacao());
        assertNotNull(persistedComputador.getId_setor());

        assertEquals(persistedComputador.getId(), computador.getId());
        assertEquals("info_04", persistedComputador.getHostname());
        assertEquals("HP", persistedComputador.getModelo());
        assertEquals("Core i5", persistedComputador.getCpu());
        assertEquals("6GB RAM", persistedComputador.getMemoria());
        assertEquals("SSD 240GB", persistedComputador.getDisco());
        assertEquals("Windows 11", persistedComputador.getSistemaOperacional());
        assertEquals("Computador de Teste", persistedComputador.getObservacao());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException {
        computadorUpdate.setHostname("info_04 Modificado");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(computadorUpdate)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ComputadorDTOTest persistedComputador = objectMapper.readValue(content, ComputadorDTOTest.class);
        computador = persistedComputador;

        assertNotNull(persistedComputador);
        assertNotNull(persistedComputador.getId());
        assertNotNull(persistedComputador.getHostname());
        assertNotNull(persistedComputador.getModelo());
        assertNotNull(persistedComputador.getCpu());
        assertNotNull(persistedComputador.getMemoria());
        assertNotNull(persistedComputador.getDisco());
        assertNotNull(persistedComputador.getSistemaOperacional());
        assertNotNull(persistedComputador.getObservacao());
        assertNotNull(persistedComputador.getId_setor());

        assertEquals(persistedComputador.getId(), computador.getId());
        assertEquals("info_04 Modificado", persistedComputador.getHostname());
        assertEquals("HP", persistedComputador.getModelo());
        assertEquals("Core i5", persistedComputador.getCpu());
        assertEquals("6GB RAM", persistedComputador.getMemoria());
        assertEquals("SSD 240GB", persistedComputador.getDisco());
        assertEquals("Windows 11", persistedComputador.getSistemaOperacional());
        assertEquals("Computador de Teste", persistedComputador.getObservacao());
    }

    @Test
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockComputador();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParams("id", computador.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ComputadorDTOTest persistedComputador = objectMapper.readValue(content, ComputadorDTOTest.class);
        computador = persistedComputador;

        assertNotNull(persistedComputador);
        assertNotNull(persistedComputador.getId());
        assertNotNull(persistedComputador.getHostname());
        assertNotNull(persistedComputador.getModelo());
        assertNotNull(persistedComputador.getCpu());
        assertNotNull(persistedComputador.getMemoria());
        assertNotNull(persistedComputador.getDisco());
        assertNotNull(persistedComputador.getSistemaOperacional());
        assertNotNull(persistedComputador.getObservacao());
        assertNotNull(persistedComputador.getId_setor());

        assertEquals(persistedComputador.getId(), computador.getId());
        assertEquals("info_04 Modificado", persistedComputador.getHostname());
        assertEquals("HP", persistedComputador.getModelo());
        assertEquals("Core i5", persistedComputador.getCpu());
        assertEquals("6GB RAM", persistedComputador.getMemoria());
        assertEquals("SSD 240GB", persistedComputador.getDisco());
        assertEquals("Windows 11", persistedComputador.getSistemaOperacional());
        assertEquals("Computador de Teste", persistedComputador.getObservacao());
    }

    @Test
    @Order(4)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", computador.getId())
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

        WrapperComputadorDTO wrapper = objectMapper.readValue(content, WrapperComputadorDTO.class);
        var emails = wrapper.getEmbeded().getComputadores();

        ComputadorDTOTest computadrOne = emails.get(0);

        assertNotNull(computadrOne);
        assertNotNull(computadrOne.getId());
        assertNotNull(computadrOne.getHostname());
        assertNotNull(computadrOne.getModelo());
        assertNotNull(computadrOne.getCpu());
        assertNotNull(computadrOne.getMemoria());
        assertNotNull(computadrOne.getDisco());
        assertNotNull(computadrOne.getSistemaOperacional());
        assertNotNull(computadrOne.getObservacao());
        assertNotNull(computadrOne.getId_setor());

        assertEquals("1d3808f8-da36-44ea-8fbd-79653a80002s", computadrOne.getId());
        assertEquals("info_02", computadrOne.getHostname());
        assertEquals("Dell", computadrOne.getModelo());
        assertEquals("Core i5", computadrOne.getCpu());
        assertEquals("8GB RAM", computadrOne.getMemoria());
        assertEquals("SSD 240GB", computadrOne.getDisco());
        assertEquals("Windows 10", computadrOne.getSistemaOperacional());
        assertEquals("Computador de Douglas", computadrOne.getObservacao());
    }

    @Test
    @Order(6)
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

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/computador/v1/1d3808f8-da36-44ea-8fbd-79653a80002s\"}}"));

        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/computador/v1?direction=asc&page=0&size=1&sort=hostname,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/computador/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/computador/v1?direction=asc&page=1&size=1&sort=hostname,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/computador/v1?direction=asc&page=1&size=1&sort=hostname,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":2,\"totalPages\":2,\"number\":0}}"));
    }

    @Test
    @Order(7)
    public void testFindByConta() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("nome", "douglas@gmail.com")
                .when()
                .get("/computador")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        assertTrue(content.contains("\"id\":\"1d3808f8-da36-44ea-8fbd-79653a80002s\""));
        assertTrue(content.contains("\"hostname\":\"info_02\""));
        assertTrue(content.contains("\"modelo\":\"Dell\""));
        assertTrue(content.contains("\"cpu\":\"Core i5\""));
        assertTrue(content.contains("\"memoria\":\"8GB RAM\""));
        assertTrue(content.contains("\"disco\":\"SSD 240GB\""));
        assertTrue(content.contains("\"observacao\":\"Computador de Douglas\""));
        assertTrue(content.contains("\"sistemaOperacional\":\"Windows 10\""));
        assertTrue(content.contains("\"id_setor\":\"7bf808f8-da36-44ea-8fbd-79653a80023e\""));
    }

    @Test
    @Order(8)
    public void findByIdSetor() throws JsonProcessingException {
        mockComputador();
        mockSetor();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParams("idSetor", setor.getId())
                .when()
                .get("setor/{idSetor}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperComputadorDTO wrapper = objectMapper.readValue(content, WrapperComputadorDTO.class);
        var emails = wrapper.getEmbeded().getComputadores();

        ComputadorDTOTest computadrOne = emails.get(0);

        assertNotNull(computadrOne);
        assertNotNull(computadrOne.getId());
        assertNotNull(computadrOne.getHostname());
        assertNotNull(computadrOne.getModelo());
        assertNotNull(computadrOne.getCpu());
        assertNotNull(computadrOne.getMemoria());
        assertNotNull(computadrOne.getDisco());
        assertNotNull(computadrOne.getSistemaOperacional());
        assertNotNull(computadrOne.getObservacao());
        assertNotNull(computadrOne.getId_setor());

        assertEquals("1d3808f8-da36-44ea-8fbd-79653a80002s", computadrOne.getId());
        assertEquals("info_02", computadrOne.getHostname());
        assertEquals("Dell", computadrOne.getModelo());
        assertEquals("Core i5", computadrOne.getCpu());
        assertEquals("8GB RAM", computadrOne.getMemoria());
        assertEquals("SSD 240GB", computadrOne.getDisco());
        assertEquals("Windows 10", computadrOne.getSistemaOperacional());
        assertEquals("Computador de Douglas", computadrOne.getObservacao());
    }

    private void mockSetor() {
        setor.setId("7bf808f8-da36-44ea-8fbd-79653a80023e");
        setor.setNome("TI");
    }

    private void mockComputador() {
        computador.setHostname("info_04");
        computador.setModelo("HP");
        computador.setCpu("Core i5");
        computador.setMemoria("6GB RAM");
        computador.setDisco("SSD 240GB");
        computador.setSistemaOperacional("Windows 11");
        computador.setObservacao("Computador de Teste");
        computador.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    private void mockComputadorUpdate() {
        computadorUpdate.setHostname("info_04");
        computadorUpdate.setModelo("HP");
        computadorUpdate.setCpu("Core i5");
        computadorUpdate.setMemoria("6GB RAM");
        computadorUpdate.setDisco("SSD 240GB");
        computadorUpdate.setSistemaOperacional("Windows 11");
        computadorUpdate.setObservacao("Computador de Teste");
        computadorUpdate.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }
}
