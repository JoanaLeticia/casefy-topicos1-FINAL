
package com.casefy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.casefy.dto.BoletoBancarioDTO;
import com.casefy.dto.CapinhaDTO;
import com.casefy.dto.CartaoCreditoDTO;
import com.casefy.dto.CidadeDTO;
import com.casefy.dto.ClienteDTO;
import com.casefy.dto.ClienteResponseDTO;
import com.casefy.dto.EnderecoDTO;
import com.casefy.dto.EstadoDTO;
import com.casefy.dto.ItemVendaDTO;
import com.casefy.dto.LoginDTO;
import com.casefy.dto.PagamentoDTO;
import com.casefy.dto.PedidoDTO;
import com.casefy.dto.PedidoResponseDTO;
import com.casefy.dto.PixDTO;
import com.casefy.dto.TelefoneDTO;
import com.casefy.model.Cor;
import com.casefy.service.CapinhaService;
import com.casefy.service.CidadeService;
import com.casefy.service.ClienteService;
import com.casefy.service.EnderecoService;
import com.casefy.service.EstadoService;
import com.casefy.service.PedidoService;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@QuarkusTest
public class PedidoResourceTest {
    @Inject
    ClienteService clienteService;

    @Inject
    EstadoService estadoService;

    @Inject
    CidadeService cidadeService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    PedidoService pedidoService;

    @Inject
    CapinhaService capinhaService;


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
                .when().get("/pedidos")
                .then()
                .statusCode(200);
    }

    @Test
        public void testInsert() throws ParseException {
                List<PixDTO> listapix = new ArrayList<PixDTO>();
                listapix.add(new PixDTO(
                                "63984398131"));

                String dataVencimentoStr = "2025-02-03";
                LocalDate dataVencimentoCartao = LocalDate.parse(dataVencimentoStr);
                List<CartaoCreditoDTO> listaCartaoCredito = new ArrayList<CartaoCreditoDTO>();
                listaCartaoCredito.add(new CartaoCreditoDTO(
                                "Visa",
                                "1598753",
                                "123",
                                dataVencimentoCartao));

                String dataVencimento = "2025-02-10";
                LocalDate dataVencimentoBoleto = LocalDate.parse(dataVencimento);
                List<BoletoBancarioDTO> listaBoleto = new ArrayList<BoletoBancarioDTO>();
                listaBoleto.add(new BoletoBancarioDTO(
                                "Nubank",
                                "951478",
                                dataVencimentoBoleto));

                PagamentoDTO dtoPagamento = new PagamentoDTO(
                                "Avista",
                                520.25F,
                                listapix,
                                listaBoleto,
                                listaCartaoCredito);

                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                Long idendereco = enderecoService.insert(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio)).id();

                Cor corCha = Cor.AMARELO;
                Long idModelo = 1L; 
                BigDecimal valor = BigDecimal.valueOf(25.15); 
                Integer quantEstoque = 2; 

                List<ItemVendaDTO> listaItens = new ArrayList<ItemVendaDTO>();
                listaItens.add(new ItemVendaDTO(2, 22.5, 1L));

                PedidoDTO dtoPedido = new PedidoDTO(idendereco, idModelo, listaItens);
                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(dtoPedido)
                                .when().post("/pagamentos")
                                .then()
                                .statusCode(201)
                                .body(
                                                "id", notNullValue());
        }

    @Test
    public void testRemovePedido() throws Exception {
        List<PixDTO> listapix = new ArrayList<PixDTO>();
                listapix.add(new PixDTO(
                                "63984398131"));

                String dataVencimentoStr = "2025-02-03";
                LocalDate dataVencimentoCartao = LocalDate.parse(dataVencimentoStr);
                List<CartaoCreditoDTO> listaCartaoCredito = new ArrayList<CartaoCreditoDTO>();
                listaCartaoCredito.add(new CartaoCreditoDTO(
                                "Visa",
                                "1598753",
                                "123",
                                dataVencimentoCartao));

                String dataVencimento = "2025-02-10";
                LocalDate dataVencimentoBoleto = LocalDate.parse(dataVencimento);
                List<BoletoBancarioDTO> listaBoleto = new ArrayList<BoletoBancarioDTO>();
                listaBoleto.add(new BoletoBancarioDTO(
                                "Nubank",
                                "951478",
                                dataVencimentoBoleto));

                PagamentoDTO dtoPagamento = new PagamentoDTO(
                                "Avista",
                                520.25F,
                                listapix,
                                listaBoleto,
                                listaCartaoCredito);

                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                Long idendereco = enderecoService.insert(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio)).id();

                Cor corCha = Cor.AMARELO;
                Long idModelo = 1L; 
                BigDecimal valor = BigDecimal.valueOf(25.15); 
                Integer quantEstoque = 2; 

                Long idCapinha = capinhaService.insert(new CapinhaDTO(idModelo, corCha, dataVencimentoStr, valor, quantEstoque, dataVencimento)).id();

                List<ItemVendaDTO> listaItens = new ArrayList<ItemVendaDTO>();
                listaItens.add(new ItemVendaDTO(2, 22.5, idCapinha));

                PedidoDTO dtoPedido = new PedidoDTO(idendereco, idModelo, listaItens);
                
                List<TelefoneDTO> listaTelefone = new ArrayList<TelefoneDTO>();
                listaTelefone.add(new TelefoneDTO(
                                "63",
                                "984398131"));

                List<EnderecoDTO> listaEndereco = new ArrayList<EnderecoDTO>();
                Long idEstado5 = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio5 = cidadeService.insert(new CidadeDTO("São Paulo", idEstado5)).id();

                listaEndereco.add(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio5));

                // Criar uma data de nascimento válida
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-03");

                ClienteDTO dtoClientee = new ClienteDTO(
                                "Milly Melo",
                                "pierre@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);

                
                ClienteResponseDTO clienteInserido = clienteService.insert(dtoClientee);
                Long idPedido = clienteInserido.id();
                
                PedidoResponseDTO pedidoInserido = pedidoService.insert(dtoPedido, clienteInserido.login());
                Long idRemove = pedidoInserido.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pedidos/" + idRemove)
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindById() throws Exception {
        List<PixDTO> listapix = new ArrayList<PixDTO>();
                listapix.add(new PixDTO(
                                "63984398131"));

                String dataVencimentoStr = "2025-02-03";
                LocalDate dataVencimentoCartao = LocalDate.parse(dataVencimentoStr);
                List<CartaoCreditoDTO> listaCartaoCredito = new ArrayList<CartaoCreditoDTO>();
                listaCartaoCredito.add(new CartaoCreditoDTO(
                                "Visa",
                                "1598753",
                                "123",
                                dataVencimentoCartao));

                String dataVencimento = "2025-02-10";
                LocalDate dataVencimentoBoleto = LocalDate.parse(dataVencimento);
                List<BoletoBancarioDTO> listaBoleto = new ArrayList<BoletoBancarioDTO>();
                listaBoleto.add(new BoletoBancarioDTO(
                                "Nubank",
                                "951478",
                                dataVencimentoBoleto));

                PagamentoDTO dtoPagamento = new PagamentoDTO(
                                "Avista",
                                520.25F,
                                listapix,
                                listaBoleto,
                                listaCartaoCredito);

                Long idEstado = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio = cidadeService.insert(new CidadeDTO("São Paulo", idEstado)).id();

                Long idendereco = enderecoService.insert(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio)).id();

                Cor corCha = Cor.AMARELO;
                Long idModelo = 1L; 
                BigDecimal valor = BigDecimal.valueOf(25.15); 
                Integer quantEstoque = 2; 

                Long idCapinha = capinhaService.insert(new CapinhaDTO(idModelo, corCha, dataVencimentoStr, valor, quantEstoque, dataVencimento)).id();

                List<ItemVendaDTO> listaItens = new ArrayList<ItemVendaDTO>();
                listaItens.add(new ItemVendaDTO(2, 22.5, idCapinha));

                PedidoDTO dtoPedido = new PedidoDTO(idendereco, idModelo, listaItens);
                
                List<TelefoneDTO> listaTelefone = new ArrayList<TelefoneDTO>();
                listaTelefone.add(new TelefoneDTO(
                                "63",
                                "984398131"));

                List<EnderecoDTO> listaEndereco = new ArrayList<EnderecoDTO>();
                Long idEstado5 = estadoService.insert(new EstadoDTO("São PAulo", "SP")).id();
                Long idMunicipio5 = cidadeService.insert(new CidadeDTO("São Paulo", idEstado5)).id();

                listaEndereco.add(new EnderecoDTO(
                                "Rua 05",
                                "13",
                                "Portão Rosa",
                                "Norte",
                                "77789-963",
                                idMunicipio5));

                // Criar uma data de nascimento válida
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-03");

                ClienteDTO dtoClientee = new ClienteDTO(
                                "Milly Melo",
                                "pierre@gmail.com",
                                "15987",
                                "159.684.456-02",
                                dataNascimento,
                                listaTelefone,
                                listaEndereco,
                                1);

                
                ClienteResponseDTO clienteInserido = clienteService.insert(dtoClientee);
                Long idPedido = clienteInserido.id();
                
                PedidoResponseDTO pedidoInserido = pedidoService.insert(dtoPedido, clienteInserido.login());
                Long idPesquisa = pedidoInserido.id();

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/pedidos/{id}", idPesquisa)
                .then()
                .statusCode(200)
                .body("id", equalTo(idPesquisa.intValue()));
    }

}
