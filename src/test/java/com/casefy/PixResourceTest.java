package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.casefy.dto.LoginDTO;
import com.casefy.dto.PixDTO;
import com.casefy.dto.PixResponseDTO;
import com.casefy.service.PixService;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class PixResourceTest {
    @Inject
    PixService administradorService;

    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new LoginDTO("victor@unitins.br", "123");

        Response response = (Response) given()
                .contentType("application/json")
                .body(auth)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.header("Authorization");
    }

    @Test
    public void testFindAll() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/pix")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        PixDTO dtoPix = new PixDTO(
                "63984398131");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoPix)
                .when().post("/pix")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "chavePix", is("63984398131"));
    }

    @Test
    public void testUpdate() {
        PixDTO dto = new PixDTO(
                "077.511.988-06");

        // inserindo um usuario
        PixResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        PixDTO dtoUpdate = new PixDTO(
                "888.511.988-06");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when().put("/pix/" + id)
                .then()
                .statusCode(204);

        PixResponseDTO usu = administradorService.findById(id);
        assertThat(usu.chavePix(), is("888.511.988-06"));

    }

    @Test
    public void testRemovePix() {
        PixDTO dto = new PixDTO(
                "63984785963");

        PixResponseDTO administradorInserido = administradorService.insert(dto);
        Long idPix = administradorInserido.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pix/" + idPix)
                .then()
                .statusCode(204);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/pix/" + idPix)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindById() {
        PixDTO dto = new PixDTO(
                "carlos@gmail.com");

        // Inserindo um usuário
        PixResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/pix/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long idNaoExistente = 9999L;

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/pix/{id}", idNaoExistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByNome() {
        String chaveExistente = "9998563254";

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("pix/search/{chave}", chaveExistente)
                .then()
                .statusCode(200);
    }

}