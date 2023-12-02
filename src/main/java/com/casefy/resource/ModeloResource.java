package com.casefy.resource;

import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.ModeloDTO;
import com.casefy.dto.ModeloResponseDTO;
import com.casefy.service.ModeloService;

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

@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloResource {

    @Inject
    ModeloService service;
    private static final Logger LOG = Logger.getLogger(ModeloResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(ModeloDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Modelo.");
        try {
            LOG.info("Inserindo Modelo");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Modelo.");
            LOG.debug("Debug de inserção de Modelo.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(ModeloDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Modelo");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Modelo.");
            LOG.debug("Debug da atualização de Modelo.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Modelo");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Modelo.");
            LOG.debug("Debug da exclusão do Modelo.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Modelo.");
        LOG.debug("Debug de busca de lista de Modelo.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            ModeloResponseDTO a = service.findById(id);
            LOG.info("Buscando um Modelo por ID.");
            LOG.debug("Debug de busca de ID de Modelo.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Modelo por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("nome") String cep) {
        try {
            LOG.info("Buscando Modelo pelo nome.");
            LOG.debug("Debug de busca de Modelo pelo nome.");
            return Response.ok(service.findByNome(cep)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Modelo pelo nome.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
