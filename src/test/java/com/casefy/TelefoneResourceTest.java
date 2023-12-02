package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.casefy.dto.LoginDTO;
import com.casefy.dto.TelefoneDTO;
import com.casefy.dto.TelefoneResponseDTO;
import com.casefy.service.TelefoneService;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class TelefoneResourceTest {
    @Inject
    TelefoneService administradorService;
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
                .when().get("/administradores")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        TelefoneDTO dtoTelefone = new TelefoneDTO(
                "63",
                "984398131");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoTelefone)
                .when().post("/telefones")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "codigoArea", is("63"),
                        "numero", is("984398131"));
    }

    @Test
    public void testUpdate() {
        TelefoneDTO dto = new TelefoneDTO(
                "63",
                "984398131");

        // inserindo um usuario
        TelefoneResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        TelefoneDTO dtoUpdate = new TelefoneDTO(
                "62",
                "984398131");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when().put("/telefones/" + id)
                .then()
                .statusCode(204);

        TelefoneResponseDTO usu = administradorService.findById(id);
        assertThat(usu.codigoArea(), is("62"));
        assertThat(usu.numero(), is("984398131"));

    }

    @Test
    public void testRemoveTelefone() {
        TelefoneDTO dto = new TelefoneDTO(
                "62",
                "984398131");

        TelefoneResponseDTO administradorInserido = administradorService.insert(dto);
        Long idTelefone = administradorInserido.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/felefones/" + idTelefone)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindById() {
        TelefoneDTO dto = new TelefoneDTO(
                "99",
                "984759685");

        // Inserindo um usuário
        TelefoneResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/telefones/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long idNaoExistente = 9999L;

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/administradores/{id}", idNaoExistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByNome() {
        String numeroExistente = "98478-3692";

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/telefones/search/numero/{numero}", numeroExistente)
                .then()
                .statusCode(200)
                .body("numero[0]", equalTo(numeroExistente));
    }

    @Test
    public void testFindByNomeNotFound() {
        String numeroNaoExistente = "00000-888";

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/telefones/search/numero/{numero}", numeroNaoExistente)
                .then()
                .statusCode(200)
                .body("$", hasSize(equalTo(0)));
    }

}