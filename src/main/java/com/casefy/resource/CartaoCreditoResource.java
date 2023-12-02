package com.casefy.resource;

import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.CartaoCreditoDTO;
import com.casefy.dto.CartaoCreditoResponseDTO;
import com.casefy.service.CartaoCreditoService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cartaoCredito")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaoCreditoResource {
    @Inject
    CartaoCreditoService service;

    private static final Logger LOG = Logger.getLogger(CartaoCreditoResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(CartaoCreditoDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Cartao Credito.");
        try {
            LOG.info("Inserindo Cartao Credito");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Cartao Credito.");
            LOG.debug("Debug de inserção de Cartao Credito.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(CartaoCreditoDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Cartao Credito");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Cartao Credito.");
            LOG.debug("Debug da atualização de Cartao Credito.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Cartao Credito");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Cartao Credito.");
            LOG.debug("Debug da exclusão do Cartao Credito.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Cartao Credito.");
        LOG.debug("Debug de busca de lista de Cartao Credito.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            CartaoCreditoResponseDTO a = service.findById(id);
            LOG.info("Buscando um Cartao Credito por ID.");
            LOG.debug("Debug de busca de ID de Cartao Credito.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Cartao Credito por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/bandeira/{bandeira}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("bandeira") String nome) {
        try {
            LOG.info("Buscando Cartao Credito pela bandeira.");
            LOG.debug("Debug de busca de Cartao Credito.");
            return Response.ok(service.findByBandeira(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Cartao Credito pela bandeira.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
