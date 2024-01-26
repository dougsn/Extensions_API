package com.extensions.integrationtests.controller.autorization;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.computador.ComputadorDTOTest;
import com.extensions.integrationtests.dto.computador.ComputadorUpdateDTOTest;
import com.extensions.integrationtests.dto.setor.SetorDTO;
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
public class ComputadorControllerAuthorizationTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static RequestSpecification specificationWithoutAuthorization;
    private static ObjectMapper objectMapper;
    private static ComputadorDTOTest computador;
    private static ComputadorUpdateDTOTest computadorUpdate;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;
    private static AuthenticationResponse authenticationResponseWithoutAuthorization;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        computador = new ComputadorDTOTest();
        computadorUpdate = new ComputadorUpdateDTOTest();
        setor = new SetorDTO();
        authenticationResponse = new AuthenticationResponse();
        authenticationResponseWithoutAuthorization = new AuthenticationResponse();
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
    public void authorizationWithoutAuthorization() throws JsonProcessingException {
        AuthenticationRequest user = new AuthenticationRequest("Common User", "admin123");

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

        authenticationResponseWithoutAuthorization = objectMapper.readValue(token, AuthenticationResponse.class);
        specificationWithoutAuthorization = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARM_AUTHORIZATION, "Bearer " + authenticationResponseWithoutAuthorization.getToken())
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
        mockEmail();
        mockEmailUpdate();
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
    public void testCreateWithoutAuthorization() {
        computador.setHostname("info_04");
        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(computador)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    @Test
    @Order(3)
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
    @Order(4)
    public void testUpdateWithoutAuthorization() {
        computadorUpdate.setHostname("info_04 Modificado");

        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(computadorUpdate)
                .when()
                .put()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    @Test
    @Order(5)
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
    @Order(3)
    public void testDeleteWithoutAuthorization() {
        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", computador.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(403);
    }

    private void mockSetor() {
        setor.setId("7bf808f8-da36-44ea-8fbd-79653a80023e");
        setor.setNome("TI");
    }

    private void mockEmail() {
        computador.setHostname("info_04");
        computador.setModelo("HP");
        computador.setCpu("Core i5");
        computador.setMemoria("6GB RAM");
        computador.setDisco("SSD 240GB");
        computador.setSistemaOperacional("Windows 11");
        computador.setObservacao("Computador de Teste");
        computador.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    private void mockEmailUpdate() {
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
