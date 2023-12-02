package com.casefy.resource;


import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.PixDTO;
import com.casefy.dto.PixResponseDTO;
import com.casefy.service.PixService;

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

@Path("/pix")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PixResource {


    @Inject
    PixService service;
    private static final Logger LOG = Logger.getLogger(PixResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(PixDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Pix.");
        try {
            LOG.info("Inserindo Pix");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Pix.");
            LOG.debug("Debug de inserção de Pix.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(PixDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Pix");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Pix.");
            LOG.debug("Debug da atualização de Pix.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Pix");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Pix.");
            LOG.debug("Debug da exclusão do Pix.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Pix.");
        LOG.debug("Debug de busca de lista de Pix.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            PixResponseDTO a = service.findById(id);
            LOG.info("Buscando um Pix por ID.");
            LOG.debug("Debug de busca de ID de Pix.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Pix por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/{chavePix}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("chavePix") String chavePix) {
        try {
            LOG.info("Buscando Pix pela chave Pix.");
            LOG.debug("Debug de busca de Pix pela chave Pix.");
            return Response.ok(service.findByChave(chavePix)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Pix pela chave Pix.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}