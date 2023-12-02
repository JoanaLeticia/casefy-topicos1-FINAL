package com.casefy.resource;

import org.jboss.logging.Logger;

import com.casefy.application.Result;
import com.casefy.dto.PagamentoDTO;
import com.casefy.dto.PagamentoResponseDTO;
import com.casefy.service.PagamentoService;

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

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {


    @Inject
    PagamentoService service;
    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(PagamentoDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Pagamento.");
        try {
            LOG.info("Inserindo Pagamento");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao inserir um Pagamento.");
            LOG.debug("Debug de inserção de Pagamento.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(PagamentoDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Pagamento");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao atualizar um Pagamento.");
            LOG.debug("Debug da atualização de Pagamento.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Pagamento");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.error("Erro ao deletar um Pagamento.");
            LOG.debug("Debug da exclusão do Pagamento.");
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
            PagamentoResponseDTO a = service.findById(id);
            LOG.info("Buscando um Pagamento por ID.");
            LOG.debug("Debug de busca de ID de Pagamento.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Pagamento por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/tipoPagamento/{tipo}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("tipo") String tipo) {
        try {
            LOG.info("Buscando Pagamento pelo tipo.");
            LOG.debug("Debug de busca de Pagamento pelo tipo.");
            return Response.ok(service.findByTipo(tipo)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar Pagamento pelo tipo.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}