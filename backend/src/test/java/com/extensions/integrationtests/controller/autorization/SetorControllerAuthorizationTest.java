package com.extensions.integrationtests.controller.autorization;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
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
public class SetorControllerAuthorizationTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static RequestSpecification specificationWithoutAuthorization;
    private static ObjectMapper objectMapper;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;
    private static AuthenticationResponse authenticationResponseWithoutAuthorization;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


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
                .setBasePath("/api/setor/v1")
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
                .setBasePath("/api/setor/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }


    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockSetor();
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(setor)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        SetorDTO persistedBook = objectMapper.readValue(content, SetorDTO.class);
        setor = persistedBook;

        assertNotNull(persistedBook);
        assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getNome());

        assertEquals(persistedBook.getId(), setor.getId());


        assertEquals("Teste", persistedBook.getNome());
    }

    @Test
    @Order(2)
    public void testCreateWithoutAuthorization() {
        mockSetor();
        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(setor)
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
        setor.setNome("Teste Modificado");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(setor)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        SetorDTO persistedBook = objectMapper.readValue(content, SetorDTO.class);
        setor = persistedBook;

        assertNotNull(persistedBook);
        assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getNome());

        assertEquals(persistedBook.getId(), setor.getId());
        assertEquals("Teste Modificado", persistedBook.getNome());
    }

    @Test
    @Order(4)
    public void testUpdateWithoutAuthorization() {
        setor.setNome("Teste Modificado");

        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(setor)
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
                .pathParam("id", "bfdb221s-da36-44ea-8fbd-79653a80023e")
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
                .pathParam("id", "bfdb221s-da36-44ea-8fbd-79653a80023e")
                .when()
                .delete("{id}")
                .then()
                .statusCode(403);
    }

    private void mockSetor() {
        setor.setNome("Teste");
    }
}
