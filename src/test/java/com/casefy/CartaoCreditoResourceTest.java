package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.casefy.dto.CartaoCreditoDTO;
import com.casefy.dto.CartaoCreditoResponseDTO;
import com.casefy.dto.LoginDTO;
import com.casefy.service.CartaoCreditoService;

import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class CartaoCreditoResourceTest {
    @Inject
    CartaoCreditoService administradorService;

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
                .when().get("/cartaoCredito")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        CartaoCreditoDTO dtoCartaoCredito = new CartaoCreditoDTO(
                "Visa",
                "1598753",
                "123",
                dataVencimento);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoCartaoCredito)
                .when().post("/cartaoCredito")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "bandeira", is("Visa"),
                        "numeroCartao", is("1598753"));
    }

    @Test
    public void testUpdate() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        CartaoCreditoDTO dto = new CartaoCreditoDTO(
                "Visa",
                "1598753",
                "123",
                dataVencimento);

        CartaoCreditoResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        CartaoCreditoDTO dtoUpdate = new CartaoCreditoDTO(
                "Visa",
                "1598753",
                "123",
                dataVencimento);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when().put("/cartaoCredito/" + id)
                .then()
                .statusCode(204);

        CartaoCreditoResponseDTO usu = administradorService.findById(id);
        assertThat(usu.bandeira(), is("Visa"));
        assertThat(usu.numeroCartao(), is("1598753"));

    }

    @Test
    public void testRemoveCartaoCredito() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        CartaoCreditoDTO dto = new CartaoCreditoDTO(
                "Visa",
                "1598753",
                "123",
                dataVencimento);

        CartaoCreditoResponseDTO administradorInserido = administradorService.insert(dto);
        Long idCartaoCredito = administradorInserido.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/cartaoCredito/" + idCartaoCredito)
                .then()
                .statusCode(204);
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/cartaoCredito/" + idCartaoCredito)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindById() {
        String dataVencimentoStr = "2025-02-03";
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        CartaoCreditoDTO dto = new CartaoCreditoDTO(
                "Visa",
                "1598753",
                "123",
                dataVencimento);

        // Inserindo um usuário
        CartaoCreditoResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/cartaoCredito/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long idNaoExistente = 9999L;

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/cartaoCredito/{id}", idNaoExistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByNome() {
        String nomeExistente = "Visa";

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("cartaoCredito/search/{bandeira}", nomeExistente)
                .then()
                .statusCode(200)
                .body("bandeira[0]", equalTo(nomeExistente));
    }

}
