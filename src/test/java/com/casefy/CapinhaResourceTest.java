package com.casefy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.casefy.dto.CapinhaDTO;
import com.casefy.dto.CapinhaResponseDTO;
import com.casefy.model.Cor;
import com.casefy.service.CapinhaService;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@QuarkusTest
public class CapinhaResourceTest {

    @Inject
    EntityManager em;
    
    @Inject
    CapinhaService capinhaService;

    @Test
    public void testFindAll() {
        given()
            .when().get("/capinhas")
            .then()
            .statusCode(200);
    }

    @Test
    @Transactional
    public void testInsert() {
        CapinhaDTO dtoCapinha = new CapinhaDTO(
            null,
            Cor.AMARELO,
            "Nome teste",
            BigDecimal.valueOf(25.00),
            15,
            "Descrição teste"
        );

        

        given()
                .contentType(ContentType.JSON)
                .body(dtoCapinha)
                .when().post("/capinhas")
                .then()
                .statusCode(201)
                .body(
                        "id", notNullValue(),
                        "nome", is("Nome teste"),
                        "valor", equalTo((float) 25.00),
                        "quantEstoque", equalTo(15),
                        "descricao", is("Descrição teste"));
    }

    @Test
    public void testUpdate() {
        CapinhaDTO dtoCapinha = new CapinhaDTO(
            null,
            Cor.AMARELO,
            "Nome teste",
            BigDecimal.valueOf(25.00),
            15,
            "Descrição teste"
        );

        CapinhaResponseDTO capinhaTest = capinhaService.insert(dtoCapinha);
        Long id = capinhaTest.id();
        
        CapinhaDTO dtoCapinhaUpdate = new CapinhaDTO(
            null,
            Cor.BRANCO,
            "Nome teste2",
            BigDecimal.valueOf(25.0),
            15,
            "Descrição teste"
        );

        given()
                .contentType(ContentType.JSON)
                .body(dtoCapinhaUpdate)
                .when().put("/capinhas/" + id)
                .then()
                .statusCode(204);

        CapinhaResponseDTO capinha = capinhaService.findById(id);
        assertThat(capinha.cor(), is(Cor.BRANCO));
        assertThat(capinha.nome(), is("Nome teste2"));


    }

    @Test
    public void testRemoveCapinha() {
        CapinhaDTO dtoCapinha = new CapinhaDTO(
            null,
            Cor.AMARELO,
            "Nome teste",
            BigDecimal.valueOf(25.00),
            15,
            "Descrição teste"
        );

        CapinhaResponseDTO capinhaInserida = capinhaService.insert(dtoCapinha);
        Long idCapinha = capinhaInserida.id();

        given()
            .when()
            .delete("/capinhas/" + idCapinha)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/capinhas/" + idCapinha)
            .then()
            .statusCode(404);
    }

    @Test
    public void testFindById() {
        CapinhaDTO dtoCapinha = new CapinhaDTO(
            null,
            Cor.AMARELO,
            "Nome teste",
            BigDecimal.valueOf(25.00),
            15,
            "Descrição teste"
        );

        CapinhaResponseDTO capinhaTest = capinhaService.insert(dtoCapinha);
        Long id = capinhaTest.id();

        given()
            .when().get("/capinhas/{id}", id)
            .then()
            .statusCode(200)
            .body("id", equalTo(id.intValue()));
    }

    @Test
    public void testFindByIdNotFound() {
        Long NotExistingId = 9999L;

        given()
            .when().get("/capinhas/{id}", NotExistingId)
            .then()
            .statusCode(404);
    }

    /*@Test
    public void testFindByNome() {
        String nomeExistente = "Nome teste";

        given()
                .when().get("/capinhas/search/nome/{nome}", nomeExistente)
                .then()
                .statusCode(200)
                .body("nome[0]", equalTo(nomeExistente));
    }*/

}
