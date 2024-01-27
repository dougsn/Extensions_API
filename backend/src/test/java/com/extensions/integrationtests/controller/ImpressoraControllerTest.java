package com.extensions.integrationtests.controller;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.impressora.ImpressoraDTOTest;
import com.extensions.integrationtests.dto.impressora.ImpressoraUpdateDTOTest;
import com.extensions.integrationtests.dto.setor.SetorDTO;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.integrationtests.wrappers.impressoras.WrapperImpressoraDTO;
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
public class ImpressoraControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static ImpressoraDTOTest impressora;
    private static ImpressoraUpdateDTOTest impressoraUpdate;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        impressora = new ImpressoraDTOTest();
        impressoraUpdate = new ImpressoraUpdateDTOTest();
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
                .setBasePath("/api/impressora/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockSetor();
        mockImpressora();
        mockImpressoraUpdate();
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(impressora)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        ImpressoraDTOTest persistedImpressora = objectMapper.readValue(content, ImpressoraDTOTest.class);
        impressora = persistedImpressora;
        impressoraUpdate.setId(persistedImpressora.getId());


        assertNotNull(persistedImpressora);
        assertNotNull(persistedImpressora.getId());
        assertNotNull(persistedImpressora.getMarca());
        assertNotNull(persistedImpressora.getModelo());
        assertNotNull(persistedImpressora.getIp());
        assertNotNull(persistedImpressora.getTonner());
        assertNotNull(persistedImpressora.getObservacao());
        assertNotNull(persistedImpressora.getId_setor());


        assertEquals(impressora.getId(), persistedImpressora.getId());
        assertEquals("HP", persistedImpressora.getMarca());
        assertEquals("Tank", persistedImpressora.getModelo());
        assertEquals("192", persistedImpressora.getIp());
        assertEquals("Tonner Tank", persistedImpressora.getTonner());
        assertEquals("Impressora de Douglas", persistedImpressora.getObservacao());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException {
        impressoraUpdate.setIp("194");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(impressoraUpdate)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ImpressoraDTOTest persistedImpressora = objectMapper.readValue(content, ImpressoraDTOTest.class);
        impressora = persistedImpressora;

        assertNotNull(persistedImpressora);
        assertNotNull(persistedImpressora.getId());
        assertNotNull(persistedImpressora.getMarca());
        assertNotNull(persistedImpressora.getModelo());
        assertNotNull(persistedImpressora.getIp());
        assertNotNull(persistedImpressora.getTonner());
        assertNotNull(persistedImpressora.getObservacao());
        assertNotNull(persistedImpressora.getId_setor());


        assertEquals(impressora.getId(), persistedImpressora.getId());
        assertEquals("HP", persistedImpressora.getMarca());
        assertEquals("Tank", persistedImpressora.getModelo());
        assertEquals("194", persistedImpressora.getIp());
        assertEquals("Tonner Tank", persistedImpressora.getTonner());
        assertEquals("Impressora de Douglas", persistedImpressora.getObservacao());
    }

    @Test
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockImpressora();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParams("id", impressora.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ImpressoraDTOTest persistedImpressora = objectMapper.readValue(content, ImpressoraDTOTest.class);
        impressora = persistedImpressora;

        assertNotNull(persistedImpressora);
        assertNotNull(persistedImpressora.getId());
        assertNotNull(persistedImpressora.getMarca());
        assertNotNull(persistedImpressora.getModelo());
        assertNotNull(persistedImpressora.getIp());
        assertNotNull(persistedImpressora.getTonner());
        assertNotNull(persistedImpressora.getObservacao());
        assertNotNull(persistedImpressora.getId_setor());


        assertEquals(impressora.getId(), persistedImpressora.getId());
        assertEquals("HP", persistedImpressora.getMarca());
        assertEquals("Tank", persistedImpressora.getModelo());
        assertEquals("194", persistedImpressora.getIp());
        assertEquals("Tonner Tank", persistedImpressora.getTonner());
        assertEquals("Impressora de Douglas", persistedImpressora.getObservacao());
    }

    @Test
    @Order(4)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", impressora.getId())
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

        WrapperImpressoraDTO wrapper = objectMapper.readValue(content, WrapperImpressoraDTO.class);
        var impressoras = wrapper.getEmbeded().getimpressoras();

        ImpressoraDTOTest impressoraOne = impressoras.get(0);

        assertNotNull(impressoraOne);
        assertNotNull(impressoraOne.getId());
        assertNotNull(impressoraOne.getMarca());
        assertNotNull(impressoraOne.getModelo());
        assertNotNull(impressoraOne.getIp());
        assertNotNull(impressoraOne.getTonner());
        assertNotNull(impressoraOne.getObservacao());
        assertNotNull(impressoraOne.getId_setor());


        assertEquals("2f1808f8-da36-44ea-8fbd-79653a80002s", impressoraOne.getId());
        assertEquals("Brother", impressoraOne.getMarca());
        assertEquals("Tank", impressoraOne.getModelo());
        assertEquals("192.168.0.3", impressoraOne.getIp());
        assertEquals("Tonner Brother", impressoraOne.getTonner());
        assertEquals("Impressora de Carlos", impressoraOne.getObservacao());
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

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/impressora/v1/2f1808f8-da36-44ea-8fbd-79653a80002s\"}}"));

        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/impressora/v1?direction=asc&page=0&size=1&sort=marca,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/impressora/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/impressora/v1?direction=asc&page=1&size=1&sort=marca,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/impressora/v1?direction=asc&page=1&size=1&sort=marca,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":2,\"totalPages\":2,\"number\":0}}"));
    }

    @Test
    @Order(7)
    public void testFindByConta() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("marca", "HP")
                .when()
                .get("/impressora")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        assertTrue(content.contains("\"id\":\"1d3808f8-da36-44ea-8fbd-79653a80002s\""));
        assertTrue(content.contains("\"marca\":\"HP\""));
        assertTrue(content.contains("\"modelo\":\"Tank\""));
        assertTrue(content.contains("\"ip\":\"192.168.0.2\""));
        assertTrue(content.contains("\"tonner\":\"Tonner HP\""));
        assertTrue(content.contains("\"observacao\":\"Impressora de Douglas\""));
        assertTrue(content.contains("\"id_setor\":\"7bf808f8-da36-44ea-8fbd-79653a80023e\""));
    }

    @Test
    @Order(8)
    public void findByIdSetor() throws JsonProcessingException {
        mockImpressora();
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

        WrapperImpressoraDTO wrapper = objectMapper.readValue(content, WrapperImpressoraDTO.class);
        var emails = wrapper.getEmbeded().getimpressoras();

        ImpressoraDTOTest impressoraOne = emails.get(0);

        assertNotNull(impressoraOne);
        assertNotNull(impressoraOne.getId());
        assertNotNull(impressoraOne.getMarca());
        assertNotNull(impressoraOne.getModelo());
        assertNotNull(impressoraOne.getIp());
        assertNotNull(impressoraOne.getTonner());
        assertNotNull(impressoraOne.getObservacao());
        assertNotNull(impressoraOne.getId_setor());


        assertEquals("1d3808f8-da36-44ea-8fbd-79653a80002s", impressoraOne.getId());
        assertEquals("HP", impressoraOne.getMarca());
        assertEquals("Tank", impressoraOne.getModelo());
        assertEquals("192.168.0.2", impressoraOne.getIp());
        assertEquals("Tonner HP", impressoraOne.getTonner());
        assertEquals("Impressora de Douglas", impressoraOne.getObservacao());
    }

    private void mockSetor() {
        setor.setId("7bf808f8-da36-44ea-8fbd-79653a80023e");
        setor.setNome("TI");
    }

    private void mockImpressora() {
        impressora.setMarca("HP");
        impressora.setModelo("Tank");
        impressora.setIp("192");
        impressora.setTonner("Tonner Tank");
        impressora.setObservacao("Impressora de Douglas");
        impressora.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    private void mockImpressoraUpdate() {
        impressoraUpdate.setMarca("HP");
        impressoraUpdate.setModelo("Tank");
        impressoraUpdate.setIp("192");
        impressoraUpdate.setTonner("Tonner Tank");
        impressoraUpdate.setObservacao("Impressora de Douglas");
        impressoraUpdate.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }
}
