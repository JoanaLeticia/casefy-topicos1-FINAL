package com.casefy.resource;


import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.BoletoBancarioDTO;
import com.casefy.dto.BoletoBancarioResponseDTO;
import com.casefy.service.BoletoBancarioService;

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

@Path("/boletosBancarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoletoBancarioResource {
@Inject
    BoletoBancarioService service;

    private static final Logger LOG = Logger.getLogger(BoletoBancarioResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(BoletoBancarioDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Boleto Bancario.");
        try {
            LOG.info("Inserindo Boleto Bancario");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de inserção de Boleto Bancario.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(BoletoBancarioDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Boleto Bancario");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da atualização de Boleto Bancario.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Boleto Bancario");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da exclusão do Boleto Bancario.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Boleto Bancario.");
        LOG.debug("Debug de busca de lista de Boleto Bancario.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            BoletoBancarioResponseDTO a = service.findById(id);
            LOG.info("Buscando um Boleto Bancario por ID.");
            LOG.debug("Debug de busca de ID de Boleto Bancario.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.info("Erro ao buscar um Boleto Bancario por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/numeroDoBoleto/{numeroDoBoleto}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("numeroDoBoleto") String nome) {
        try {
            LOG.info("Buscando um Boleto Bancario numero Do Boleto.");
            LOG.debug("Debug de busca de numero Do Boleto de Boleto Bancario.");
            return Response.ok(service.findByNumeroBoleto(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.info("Erro ao buscar numero Do Boleto.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}