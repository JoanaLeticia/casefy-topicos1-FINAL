package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.casefy.dto.CidadeDTO;
import com.casefy.dto.EnderecoDTO;
import com.casefy.dto.EnderecoResponseDTO;
import com.casefy.dto.EstadoDTO;
import com.casefy.dto.LoginDTO;
import com.casefy.service.CidadeService;
import com.casefy.service.EnderecoService;
import com.casefy.service.EstadoService;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class EnderecoResourceTest {
        @Inject
        EnderecoService enderecoService;

        @Inject
        EstadoService estadoService;

        @Inject
        CidadeService cidadeService;

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
                                .when().get("/enderecos")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                EnderecoDTO enderecoDTO = new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio);
                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(enderecoDTO)
                                .when().post("/enderecos")
                                .then()
                                .statusCode(201)
                                .body(
                                                "numero", is("13"),
                                                "cep", is("77789-963"));
        }

        @Test
        public void testUpdate() {

                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                EnderecoDTO enderecoDTO = new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio);

                EnderecoResponseDTO usuarioTest = enderecoService.insert(enderecoDTO);
                Long id = usuarioTest.id();

                EnderecoDTO enderecoUpdDTO = new EnderecoDTO(
                                "Rua 05",
                                "65",
                                "Portão Rosa",
                                "Norte",
                                "77789-000",
                                idMunicipio);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(enderecoUpdDTO)
                                .when().put("/enderecos/" + id)
                                .then()
                                .statusCode(204);

                // Verificando se os dados foram atualizados no banco de dados
                EnderecoResponseDTO end = enderecoService.findById(id);
                assertThat(end.numero(), is("65"));
                assertThat(end.cep(), is("77789-000"));

        }

        @Test
        public void testRemoveEndereco() {
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                EnderecoDTO enderecoDTO = new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio);

                EnderecoResponseDTO usuarioTest = enderecoService.insert(enderecoDTO);
                Long id = usuarioTest.id();

                given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .delete("/enderecos/" + id)
                                .then()
                                .statusCode(204); // O código 204 indica que a remoção foi bem-sucedida

                given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .get("/enderecos/" + id)
                                .then()
                                .statusCode(404);
        }

        @Test
        public void testFindById() {
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                EnderecoDTO enderecoDTO = new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio);

                EnderecoResponseDTO usuarioTest = enderecoService.insert(enderecoDTO);
                Long id = usuarioTest.id();

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/{id}", id)
                                .then()
                                .statusCode(200)
                                .body("id", equalTo(id.intValue()));
        }

        @Test
        public void testFindByIdNotFound() {
                Long idNaoExistente = 9999L;

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/{id}", idNaoExistente)
                                .then()
                                .statusCode(404);
        }

        @Test
        public void testFindByCep() {
                String cepExistente = "70084-885";

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/search/cep/{cep}", cepExistente)
                                .then()
                                .statusCode(200)
                                .body("cep[0]", equalTo(cepExistente));
        }

        @Test
        public void testFindByNomeNotFound() {
                String nomeNaoExistente = "65489-000";

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/search/cep/{cep}", nomeNaoExistente)
                                .then()
                                .statusCode(200)
                                .body("$", hasSize(equalTo(0)));
        }

}
