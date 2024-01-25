package com.extensions.integrationtests.controller.autorization;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.email.EmailDTOTest;
import com.extensions.integrationtests.dto.email.EmailUpdateDTOTest;
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
public class EmailControllerAuthorizationTest extends AbstractIntegrationTest{
    private static RequestSpecification specification;
    private static RequestSpecification specificationWithoutAuthorization;
    private static ObjectMapper objectMapper;
    private static EmailDTOTest email;
    private static EmailUpdateDTOTest emailUpdate;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;
    private static AuthenticationResponse authenticationResponseWithoutAuthorization;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        email = new EmailDTOTest();
        emailUpdate = new EmailUpdateDTOTest();
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
                .setBasePath("/api/email/v1")
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
                .setBasePath("/api/email/v1")
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
                .body(email)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        EmailDTOTest persistedEmail = objectMapper.readValue(content, EmailDTOTest.class);
        email = persistedEmail;
        emailUpdate.setId(persistedEmail.getId());


        assertNotNull(persistedEmail);
        assertNotNull(persistedEmail.getId());
        assertNotNull(persistedEmail.getConta());
        assertNotNull(persistedEmail.getSenha());
        assertNotNull(persistedEmail.getId_setor());

        assertEquals(persistedEmail.getId(), email.getId());
        assertEquals("douglas@gmail.com", persistedEmail.getConta());
        assertEquals("123", persistedEmail.getSenha());
        assertEquals(persistedEmail.getId(), emailUpdate.getId());
    }

    @Test
    @Order(2)
    public void testCreateWithoutAuthorization() {
        email.setConta("douglasmodificado@gmail.com");
        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(email)
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
        emailUpdate.setConta("douglasmodificado@gmail.com");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(emailUpdate)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        EmailDTOTest persistedEmail = objectMapper.readValue(content, EmailDTOTest.class);
        email = persistedEmail;

        assertNotNull(persistedEmail);
        assertNotNull(persistedEmail.getId());
        assertNotNull(persistedEmail.getConta());
        assertNotNull(persistedEmail.getSenha());
        assertNotNull(persistedEmail.getId_setor());

        assertEquals(persistedEmail.getId(), email.getId());
        assertEquals("douglasmodificado@gmail.com", persistedEmail.getConta());
        assertEquals("123", persistedEmail.getSenha());
        assertEquals(persistedEmail.getId(), emailUpdate.getId());
    }

    @Test
    @Order(4)
    public void testUpdateWithoutAuthorization() {
        emailUpdate.setConta("douglasmodificado@gmail.com");

        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(emailUpdate)
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
                .pathParam("id", email.getId())
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
                .pathParam("id", email.getId())
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
        email.setConta("douglas@gmail.com");
        email.setSenha("123");
        email.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }

    private void mockEmailUpdate() {
        emailUpdate.setConta("douglas@gmail.com");
        emailUpdate.setSenha("123");
        emailUpdate.setId_setor("7bf808f8-da36-44ea-8fbd-79653a80023e");
    }
}
