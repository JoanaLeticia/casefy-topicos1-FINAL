package com.casefy.resource;

import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.CidadeDTO;
import com.casefy.dto.CidadeResponseDTO;
import com.casefy.service.CidadeService;

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

@Path("/cidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CidadeResource {
    @Inject
    CidadeService service;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(CidadeDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Cidade.");
        try {
            LOG.info("Inserindo Cidade");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Cidade.");
            LOG.debug("Debug de inserção de Cidade.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(CidadeDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Cidade");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Cidade.");
            LOG.debug("Debug da atualização de Cidade.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Cidade");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Cidade.");
            LOG.debug("Debug da exclusão do Cidade.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Cidade.");
        LOG.debug("Debug de busca de lista de Cidade.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            CidadeResponseDTO a = service.findById(id);
            LOG.info("Buscando um Cidade por ID.");
            LOG.debug("Debug de busca de ID de Cidade.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Cidade por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.info("Buscando Cidade pelo nome.");
            LOG.debug("Debug de busca de Cidade pelo nome.");
            return Response.ok(service.findByNome(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Cidade pelo nome.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
