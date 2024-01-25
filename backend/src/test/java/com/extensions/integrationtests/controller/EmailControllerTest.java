package com.extensions.integrationtests.controller;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.email.EmailDTOTest;
import com.extensions.integrationtests.dto.email.EmailUpdateDTOTest;
import com.extensions.integrationtests.dto.setor.SetorDTO;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.integrationtests.wrappers.email.WrapperEmailDTO;
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
public class EmailControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static EmailDTOTest email;
    private static EmailUpdateDTOTest emailUpdate;
    private static SetorDTO setor;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        email = new EmailDTOTest();
        emailUpdate = new EmailUpdateDTOTest();
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
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockEmail();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParams("id", email.getId())
                .when()
                .get("{id}")
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

        WrapperEmailDTO wrapper = objectMapper.readValue(content, WrapperEmailDTO.class);
        var emails = wrapper.getEmbeded().getEmails();

        EmailDTOTest emailOne = emails.get(0);

        assertNotNull(emailOne);
        assertNotNull(emailOne.getId());
        assertNotNull(emailOne.getConta());
        assertNotNull(emailOne.getSenha());
        assertNotNull(emailOne.getId_setor());

        assertEquals(emailOne.getId(), "1d3808f8-da36-44ea-8fbd-79653a80002s");

        assertEquals("diogo@gmail.com", emailOne.getConta());
        assertEquals("123", emailOne.getSenha());
        assertEquals(emailOne.getId_setor(), "7bf808f8-da36-44ea-8fbd-79653a80023e");
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

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/email/v1/1d3808f8-da36-44ea-8fbd-79653a80002s\"}}"));

        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/email/v1?direction=asc&page=0&size=1&sort=conta,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/email/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/email/v1?direction=asc&page=1&size=1&sort=conta,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/email/v1?direction=asc&page=1&size=1&sort=conta,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":2,\"totalPages\":2,\"number\":0}}"));
    }

    @Test
    @Order(7)
    public void testFindByConta() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("nome", "douglas@gmail.com")
                .when()
                .get("/email")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        assertTrue(content.contains("\"id\":\"1d3808f8-da36-44ea-8fbd-79653a80002s\""));
        assertTrue(content.contains("\"conta\":\"diogo@gmail.com\""));
        assertTrue(content.contains("\"senha\":\"123\""));
        assertTrue(content.contains("\"id_setor\":\"7bf808f8-da36-44ea-8fbd-79653a80023e\""));
        assertTrue(content.contains("\"nome_setor\":\"TI\""));
    }

    @Test
    @Order(8)
    public void findByIdSetor() throws JsonProcessingException {
        mockEmail();
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

        WrapperEmailDTO wrapper = objectMapper.readValue(content, WrapperEmailDTO.class);
        var emails = wrapper.getEmbeded().getEmails();

        EmailDTOTest emailOne = emails.get(0);

        assertNotNull(emailOne);
        assertNotNull(emailOne.getId());
        assertNotNull(emailOne.getConta());
        assertNotNull(emailOne.getSenha());
        assertNotNull(emailOne.getId_setor());

        assertEquals("1d3808f8-da36-44ea-8fbd-79653a80002s", emailOne.getId());
        assertEquals("diogo@gmail.com", emailOne.getConta());
        assertEquals("123", emailOne.getSenha());
        assertEquals("7bf808f8-da36-44ea-8fbd-79653a80023e", emailOne.getId_setor());
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
