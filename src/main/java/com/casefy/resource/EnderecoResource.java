package com.casefy.resource;

import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.EnderecoDTO;
import com.casefy.dto.EnderecoResponseDTO;
import com.casefy.service.EnderecoService;

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

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {
    @Inject
    EnderecoService service;

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(EnderecoDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Endereco.");
        try {
            LOG.info("Inserindo Endereco");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Endereco.");
            LOG.debug("Debug de inserção de Endereco.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(EnderecoDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Endereco");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Endereco.");
            LOG.debug("Debug da atualização de Endereco.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Endereco");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Endereco.");
            LOG.debug("Debug da exclusão do Endereco.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Endereco.");
        LOG.debug("Debug de busca de lista de Endereco.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            EnderecoResponseDTO a = service.findById(id);
            LOG.info("Buscando um Endereco por ID.");
            LOG.debug("Debug de busca de ID de Endereco.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Endereco por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/cep/{cep}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("cep") String cep) {
        try {
            LOG.info("Buscando Endereco pelo cep.");
            LOG.debug("Debug de busca de Endereco pelo cep.");
            return Response.ok(service.findByCep(cep)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Endereco pelo cep.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
