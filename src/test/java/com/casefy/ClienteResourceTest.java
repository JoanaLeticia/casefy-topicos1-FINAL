package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import io.restassured.response.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.casefy.dto.CidadeDTO;
import com.casefy.dto.ClienteDTO;
import com.casefy.dto.ClienteResponseDTO;
import com.casefy.dto.EnderecoDTO;
import com.casefy.dto.EstadoDTO;
import com.casefy.dto.LoginDTO;
import com.casefy.dto.TelefoneDTO;
import com.casefy.service.CidadeService;
import com.casefy.service.ClienteService;
import com.casefy.service.EnderecoService;
import com.casefy.service.EstadoService;

@QuarkusTest
public class ClienteResourceTest {
        @Inject
        ClienteService clienteService;

        @Inject
        CidadeService cidadeService;

        @Inject
        EstadoService estadoService;

        @Inject
        EnderecoService enderecoService;

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
                                .when().get("/clientes")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() throws ParseException {
                List<TelefoneDTO> listaTelefone = new ArrayList<TelefoneDTO>();
                listaTelefone.add(new TelefoneDTO(
                                "63",
                                "984398131"));

                List<EnderecoDTO> listaEndereco = new ArrayList<EnderecoDTO>();
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                listaEndereco.add(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio));

                // Criar uma data de nascimento válida
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-03");

                ClienteDTO dtoCliente = new ClienteDTO(
                                "Milly Melo",
                                "pierre@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(dtoCliente)
                                .when().post("/clientes")
                                .then()
                                .log().all()
                                .statusCode(201)
                                .body(
                                                "id", notNullValue(),
                                                "nome", is("Milly Melo"));
        }

        @Test
        public void testUpdate() throws Exception {
                List<TelefoneDTO> listaTelefone = new ArrayList<TelefoneDTO>();
                listaTelefone.add(new TelefoneDTO(
                                "63",
                                "984398131"));

                List<EnderecoDTO> listaEndereco = new ArrayList<EnderecoDTO>();
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                listaEndereco.add(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio));

                // Criar uma data de nascimento válida
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-03");

                ClienteDTO dtoCliente = new ClienteDTO(
                                "Milly Melo",
                                "alcantara@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);

                ClienteResponseDTO clienteTest = clienteService.insert(dtoCliente);
                Long id = clienteTest.id();

                ClienteDTO dtoUpdate = new ClienteDTO(
                                "Victor",
                                "victu@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);
                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(dtoUpdate)
                                .when().put("/clientes/" + id)
                                .then()
                                .statusCode(204);

                ClienteResponseDTO usu = clienteService.findById(id);
                assertThat(usu.nome(), is("Victor"));
                assertThat(usu.login(), is("victu@gmail.com"));

        }

        @Test
        public void testRemoveCliente() throws Exception {
                List<TelefoneDTO> listaTelefone = new ArrayList<TelefoneDTO>();
                listaTelefone.add(new TelefoneDTO(
                                "63",
                                "984398131"));

                List<EnderecoDTO> listaEndereco = new ArrayList<EnderecoDTO>();
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                listaEndereco.add(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio));

                // Criar uma data de nascimento válida
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-03");

                ClienteDTO dtoCliente = new ClienteDTO(
                                "Milly Melo",
                                "milly@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);

                ClienteResponseDTO clienteInserido = clienteService.insert(dtoCliente);
                Long idCliente = clienteInserido.id();

                given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .delete("/clientes/" + idCliente)
                                .then()
                                .statusCode(204);

                given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .get("/clientes/" + idCliente)
                                .then()
                                .statusCode(404);
        }

        @Test
        public void testFindById() throws Exception {
                List<TelefoneDTO> listaTelefone = new ArrayList<TelefoneDTO>();
                listaTelefone.add(new TelefoneDTO(
                                "63",
                                "984398131"));

                List<EnderecoDTO> listaEndereco = new ArrayList<EnderecoDTO>();
                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                listaEndereco.add(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio));

                // Criar uma data de nascimento válida
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-03");

                ClienteDTO dtoCliente = new ClienteDTO(
                                "Milly Melo",
                                "milly@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);

                ClienteResponseDTO usuarioTest = clienteService.insert(dtoCliente);
                Long id = usuarioTest.id();

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/clientes/{id}", id)
                                .then()
                                .statusCode(200)
                                .body("id", equalTo(id.intValue()));
        }

        @Test
        public void testFindByIdNotFound() {
                Long idNaoExistente = 9999L;

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/clientes/{id}", idNaoExistente)
                                .then()
                                .statusCode(404);
        }

        @Test
        public void testFindByNome() {
                String nomeExistente = "Victor Alves";

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/clientes/search/nome/{nome}", nomeExistente)
                                .then()
                                .statusCode(200)
                                .body("nome[0]", equalTo(nomeExistente));
        }

        @Test
        public void testFindByNomeNotFound() {
                String nomeNaoExistente = "Nome Inexistente";

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/clientes/search/nome/{nome}", nomeNaoExistente)
                                .then()
                                .statusCode(200)
                                .body("$", hasSize(equalTo(0)));
        }

}