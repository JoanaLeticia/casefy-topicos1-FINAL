package com.casefy.resource;


import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.PedidoDTO;
import com.casefy.dto.PedidoResponseDTO;
import com.casefy.service.PedidoService;
import com.casefy.service.UsuarioService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
    @Inject
    PedidoService service;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(PedidoDTO dto) {

        LOG.debug("Debug de inserção de Pedido.");
        try {
            LOG.info("Recuperando o identificador do usuário do token");
            String login = jwt.getSubject();
            LOG.info("Inserção do pedido Concluida!");
            PedidoResponseDTO retorno = service.insert(dto, login);
            return Response.status(201).entity(retorno).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um pedido.");
            LOG.debug("Debug de inserção de pedido.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Pedido");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Pedido.");
            LOG.debug("Debug da exclusão do Pedido.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Pagamento.");
        LOG.debug("Debug de busca de lista de Pagamento.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            PedidoResponseDTO a = service.findById(id);
            LOG.info("Buscando um pedido por ID.");
            LOG.debug("Debug de busca de ID de pedido.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um pedido por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
