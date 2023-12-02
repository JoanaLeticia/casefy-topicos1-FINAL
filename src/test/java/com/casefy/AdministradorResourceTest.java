package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.casefy.dto.AdministradorDTO;
import com.casefy.dto.AdministradorResponseDTO;
import com.casefy.dto.LoginDTO;
import com.casefy.service.AdministradorService;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AdministradorResourceTest {
    @Inject
    AdministradorService administradorService;

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
        AdministradorDTO dtoAdministrador = new AdministradorDTO(
                "Milly Melo",
                "milly@gmail.com",
                "15987",
                "159.684.456-02",
                951,
                2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoAdministrador)
                .when().post("/administradores")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "nome", is("Milly Melo"),
                        "matricula", equalTo(951));
    }

    @Test
    public void testUpdate() {
        AdministradorDTO dto = new AdministradorDTO(
                "Milly Melo",
                "milly@gmail.com",
                "15987",
                "159.684.456-02",
                951,
                2);

        // inserindo um usuario
        AdministradorResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        AdministradorDTO dtoUpdate = new AdministradorDTO(
                "Milly Melo",
                "melo@gmail.com",
                "15987",
                "159.684.456-02",
                100,
                2);

        given()
        .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when().put("/administradores/" + id)
                .then()
                .statusCode(204);

        AdministradorResponseDTO usu = administradorService.findById(id);
        assertThat(usu.nome(), is("Milly Melo"));
        assertThat(usu.matricula(), equalTo(100));

    }

    @Test
    public void testRemoveAdministrador() {
        AdministradorDTO dto = new AdministradorDTO(
                "Milly Melo",
                "milly@gmail.com",
                "15987",
                "159.684.456-02",
                951,
                2);

        AdministradorResponseDTO administradorInserido = administradorService.insert(dto);
        Long idAdministrador = administradorInserido.id();

        given()
        .header("Authorization", "Bearer " + token)
                .when()
                .delete("/administradores/" + idAdministrador)
                .then()
                .statusCode(204); // O código 204 indica que a remoção foi bem-sucedida

        given()
        .header("Authorization", "Bearer " + token)
                .when()
                .get("/administradores/" + idAdministrador)
                .then()
                .statusCode(404); // O código 404 indica que o administrador não foi encontrado (foi removido com
                                  // sucesso)
    }

    @Test
    public void testFindById() {
        AdministradorDTO dto = new AdministradorDTO(
                "Milly Melo",
                "milly@gmail.com",
                "15987",
                "159.684.456-02",
                951,
                2);

        // Inserindo um usuário
        AdministradorResponseDTO usuarioTest = administradorService.insert(dto);
        Long id = usuarioTest.id();

        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/administradores/{id}", id)
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
        String nomeExistente = "Victor Alves";

        given()
        .header("Authorization", "Bearer " + token)
                .when().get("/administradores/search/nome/{nome}", nomeExistente)
                .then()
                .statusCode(200)
                .body("nome[0]", equalTo(nomeExistente));
    }

}
