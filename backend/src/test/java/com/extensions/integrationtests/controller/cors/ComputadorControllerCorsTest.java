package com.extensions.integrationtests.controller.cors;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.computador.ComputadorDTOTest;
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
public class ComputadorControllerCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static ComputadorDTOTest computador;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        computador = new ComputadorDTOTest();
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
        mockEmail();
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
    public void testCreateWithWrongOrigin() {
        mockEmail();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .body(computador)
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
        mockEmail();

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

        assertEquals("info_04", persistedComputador.getHostname());
        assertEquals("HP", persistedComputador.getModelo());
        assertEquals("Core i5", persistedComputador.getCpu());
        assertEquals("6GB RAM", persistedComputador.getMemoria());
        assertEquals("SSD 240GB", persistedComputador.getDisco());
        assertEquals("Windows 11", persistedComputador.getSistemaOperacional());
        assertEquals("Computador de Teste", persistedComputador.getObservacao());
    }

    @Test
    @Order(4)
    public void findByIdWithWrongOrigin() {
        mockEmail();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .pathParams("id", computador.getId())
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
}
