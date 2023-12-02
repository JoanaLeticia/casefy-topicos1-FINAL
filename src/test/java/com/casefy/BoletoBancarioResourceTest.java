package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.casefy.dto.BoletoBancarioDTO;
import com.casefy.dto.BoletoBancarioResponseDTO;
import com.casefy.dto.LoginDTO;
import com.casefy.service.BoletoBancarioService;

import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTest
public class BoletoBancarioResourceTest {
    @Inject
    BoletoBancarioService administradorService;

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
                .when().get("/boletosBancarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {

        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        BoletoBancarioDTO dtoBoletoBancario = new BoletoBancarioDTO(
                "Nubank",
                "951478",
                dataVencimento);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoBoletoBancario)
                .when().post("/boletosBancarios")
                .then()
                .statusCode(201)
                .body(
                        "banco", is("Nubank"),
                        "numeroBoleto", is("951478"));
    }

    @Test
    public void testUpdate() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        BoletoBancarioDTO dtoBoletoBancario = new BoletoBancarioDTO(
                "Nubank",
                "951478",
                dataVencimento);

        // inserindo um usuario
        BoletoBancarioResponseDTO usuarioTest = administradorService.insert(dtoBoletoBancario);
        Long id = usuarioTest.id();

        BoletoBancarioDTO dtoUpdate = new BoletoBancarioDTO(
                "bradesco",
                "951478",
                dataVencimento);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when().put("/boletosBancarios/" + id)
                .then()
                .statusCode(204);

        BoletoBancarioResponseDTO usu = administradorService.findById(id);
        assertThat(usu.banco(), is("bradesco"));

    }

    @Test
    public void testRemoveBoletoBancario() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        BoletoBancarioDTO dtoBoletoBancario = new BoletoBancarioDTO(
                "Nubank",
                "951478",
                dataVencimento);

        BoletoBancarioResponseDTO administradorInserido = administradorService.insert(dtoBoletoBancario);
        Long idBoletoBancario = administradorInserido.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/boletosBancarios/" + idBoletoBancario)
                .then()
                .statusCode(204); // O código 204 indica que a remoção foi bem-sucedida

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/boletosBancarios/" + idBoletoBancario)
                .then()
                .statusCode(404); // O código 404 indica que o administrador não foi encontrado (foi removido com
                                  // sucesso)
    }

    @Test
    public void testFindById() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        BoletoBancarioDTO dtoBoletoBancario = new BoletoBancarioDTO(
                "Nubank",
                "951478",
                dataVencimento);

        BoletoBancarioResponseDTO usuarioTest = administradorService.insert(dtoBoletoBancario);
        Long id = usuarioTest.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/boletosBancarios/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long idNaoExistente = 9999L;

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/boletosBancarios/{id}", idNaoExistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByNumeroBoleto() {
        String nomeExistente = "36985214789";

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("boletosBancarios/search/{boleto}", nomeExistente)
                .then()
                .statusCode(200);
    }
}
