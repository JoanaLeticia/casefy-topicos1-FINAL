package com.casefy.resource;

import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.TelefoneDTO;
import com.casefy.dto.TelefoneResponseDTO;
import com.casefy.service.TelefoneService;

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

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    TelefoneService service;
    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(TelefoneDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Telefone.");
        try {
            LOG.info("Inserindo Telefone");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Telefone.");
            LOG.debug("Debug de inserção de Telefone.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(TelefoneDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Telefone");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Telefone.");
            LOG.debug("Debug da atualização de Telefone.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Telefone");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Telefone.");
            LOG.debug("Debug da exclusão do Telefone.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Telefone.");
        LOG.debug("Debug de busca de lista de Telefone.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            TelefoneResponseDTO a = service.findById(id);
            LOG.info("Buscando um Telefone por ID.");
            LOG.debug("Debug de busca de ID de Telefone.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Telefone por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/numero/{numero}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("numero") String numero) {
        try {
            LOG.info("Buscando Telefone pelo numero.");
            LOG.debug("Debug de busca de Telefone pelo numero.");
            return Response.ok(service.findByNumero(numero)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Telefone pelo numero.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
