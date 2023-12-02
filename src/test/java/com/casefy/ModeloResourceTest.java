package com.casefy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

import com.casefy.dto.ModeloDTO;
import com.casefy.dto.ModeloResponseDTO;
import com.casefy.service.ModeloService;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class ModeloResourceTest {
    @Inject
    ModeloService modeloService;

    @Test
    public void testFindAll() {
        given()
            .when().get("/modelos")
            .then()
            .statusCode(200);
    }

    @Test
    public void testInsert() {
        ModeloDTO dtoModelo = new ModeloDTO(
            "Nome teste",
            "Marca teste"
        );

        given()
                .contentType(ContentType.JSON)
                .body(dtoModelo)
                .when().post("/modelos")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "nome", is("Nome teste"),
                        "marca", is("Marca teste"));
    }

    @Test
    public void testUpdate() {
        ModeloDTO dtoModelo = new ModeloDTO(
            "Nome Teste",
            "Marca Teste"
        );

        ModeloResponseDTO modeloTest = modeloService.insert(dtoModelo);
        Long id = modeloTest.id();
        
        ModeloDTO dtoModeloUpdate = new ModeloDTO(
            "Nome Teste2",
            "Marca Teste2"
        );

        given()
                .contentType(ContentType.JSON)
                .body(dtoModeloUpdate)
                .when().put("/modelos/" + id)
                .then()
                .statusCode(204);

        ModeloResponseDTO modelo = modeloService.findById(id);
        assertThat(modelo.nome(), is("Nome Teste2"));
        assertThat(modelo.marca(), is("Marca Teste2"));

    }

    @Test
    public void testRemoveModelo() {
        ModeloDTO dtoModelo = new ModeloDTO(
            "Nome Teste",
            "Marca Teste"
        );

        ModeloResponseDTO modeloInserido = modeloService.insert(dtoModelo);
        Long idModelo = modeloInserido.id();

        given()
            .when()
            .delete("/modelos/" + idModelo)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/modelos/" + idModelo)
            .then()
            .statusCode(404);
    }

    @Test
    public void testFindById() {
        ModeloDTO dtoModelo = new ModeloDTO(
            "Nome Teste",
            "Marca Teste"
        );

        ModeloResponseDTO modeloTest = modeloService.insert(dtoModelo);
        Long id = modeloTest.id();

        given()
            .when().get("/modelos/{id}", id)
            .then()
            .statusCode(200)
            .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long NotExistingId = 9999L;

        given()
            .when().get("/modelos/{id}", NotExistingId)
            .then()
            .statusCode(404);
    }

    /*@Test
    public void testFindByNome() {
        String existingName = "Nome teste";

        given()
            .when().get("/modelos/search/nome/{nome}", existingName)
            .then()
            .statusCode(200)
            .body("nome[0]", equalTo(existingName));
    }*/

}
