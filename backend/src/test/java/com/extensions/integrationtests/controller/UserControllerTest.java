package com.extensions.integrationtests.controller;

import com.extensions.config.TestConfigs;
import com.extensions.integrationtests.dto.auth.AuthenticationRequest;
import com.extensions.integrationtests.dto.auth.AuthenticationResponse;
import com.extensions.integrationtests.dto.setor.SetorDTO;
import com.extensions.integrationtests.dto.user.UserDTO;
import com.extensions.integrationtests.dto.user.UserUpdateDTO;
import com.extensions.integrationtests.testcontainers.AbstractIntegrationTest;
import com.extensions.integrationtests.wrappers.WrapperSetorDTO;
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
public class UserControllerTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static UserDTO user;
    private static UserUpdateDTO userUpdate;
    private static AuthenticationResponse authenticationResponse;
    private static final String ID = "1a2b3c4d-5e6f-7g8h-9i0j-1k2l3m4n5o6";

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        user = new UserDTO();
        userUpdate = new UserUpdateDTO();
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
                .setBasePath("/api/user/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

//    @Test
//    @Order(1)
//    public void testUpdate() throws JsonProcessingException {
//        userUpdate.setId(ID);
//        userUpdate.setName("Teste Modificado");
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
//                .body(userUpdate)
//                .when()
//                .put()
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        UserUpdateDTO persistedUser = objectMapper.readValue(content, UserUpdateDTO.class);
//        user.setUsername(persistedUser.getName());
//        user.setUsername(persistedUser.getId());
//
//        System.out.println(persistedUser.getName());
//        System.out.println(persistedUser.getId());
//
//        assertNotNull(persistedUser);
//        assertNotNull(persistedUser.getId());
//        assertNotNull(persistedUser.getName());
//
//        assertEquals(persistedUser.getId(), ID);
//        assertEquals("Teste Modificado", persistedUser.getName());
//    }

    @Test
    @Order(2)
    public void findById() throws JsonProcessingException {
        mockUser();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParam("id", user.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        UserDTO persistedUser = objectMapper.readValue(content, UserDTO.class);
        user = persistedUser;

        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getId());
        assertNotNull(persistedUser.getUsername());

        assertEquals(persistedUser.getId(), user.getId());
        assertEquals("Teste Modificado", persistedUser.getUsername());
    }

    @Test
    @Order(4)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", ID)
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
        System.out.println(content);

        WrapperSetorDTO wrapper = objectMapper.readValue(content, WrapperSetorDTO.class);
        var setores = wrapper.getEmbeded().getSetores();

        SetorDTO setorOne = setores.get(0);

        assertNotNull(setorOne);
        assertNotNull(setorOne.getId());
        assertNotNull(setorOne.getNome());

        assertEquals(setorOne.getId(), "ffas3123-da36-44ea-8fbd-79653a80023e");
        assertEquals("ADM", setorOne.getNome());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/user/v1")
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
                .queryParams("page", 0, "size", 2, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/user/v1/ffas3123-da36-44ea-8fbd-79653a80023e\"}}}"));

        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/user/v1?direction=asc&page=0&size=5&sort=nome,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/user/v1?page=0&size=5&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/user/v1?direction=asc&page=1&size=5&sort=nome,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/user/v1?direction=asc&page=1&size=5&sort=nome,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":5,\"totalElements\":10,\"totalPages\":2,\"number\":0}}"));
    }


    private void mockUser() {
        user.setId(ID);
        user.setUsername("Teste");
    }

    private void mockUserUpdate(){
        userUpdate.setId(ID);
        userUpdate.setName("Teste");
    }
}
