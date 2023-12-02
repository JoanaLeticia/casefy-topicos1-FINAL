package com.casefy.resource;

import java.io.IOException;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.casefy.application.Result;
import com.casefy.dto.CapinhaDTO;
import com.casefy.dto.CapinhaResponseDTO;
import com.casefy.form.CapinhaImageForm;
import com.casefy.service.CapinhaService;
import com.casefy.service.UsuarioService;
import com.casefy.service.CapinhaFileService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/capinhas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CapinhaResource {
    @Inject
    CapinhaService service;

    @Inject
    JsonWebToken jwt;

    @Inject
    CapinhaFileService fileService;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(CapinhaResource.class);

    @POST
    @RolesAllowed({ "User", "Admin" })
    public Response insert(CapinhaDTO dto) throws Exception {
        LOG.debug("Debug de inserção de Capinha.");
        try {
            LOG.info("Inserindo Capinha");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de inserção de Capinha.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response update(CapinhaDTO dto, @PathParam("id") Long id) {
        try {
            LOG.info("Atualizando Capinha");
            service.update(dto, id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da atualização de Capinha.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Capinha");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da exclusão do Capinha.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response findAll() {
        LOG.info("Buscando todos os Capinha.");
        LOG.debug("Debug de busca de lista de Capinha.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public Response findById(@PathParam("id") Long id) {
        try {
            CapinhaResponseDTO a = service.findById(id);
            LOG.info("Buscando um Capinha por ID.");
            LOG.debug("Debug de busca de ID de Capinha.");
            return Response.ok(a).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar um Capinha por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({ "User", "Admin" })
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.info("Buscando uma Capinha pelo nome.");
            LOG.debug("Debug de busca pelo nome Capinha.");
            return Response.ok(service.findByNome(nome)).build();
        } catch (EntityNotFoundException e) {
            LOG.error("Erro ao buscar pelo nome da capinha.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // Imagens:
    @PATCH
    @Path("/upload/imagem")
    @RolesAllowed({ "User", "Admin" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagemCapinha(@MultipartForm CapinhaImageForm form) {
        String nomeImagem;
        try {
            LOG.info("Upload imagem concluido");
            LOG.debug("Upload imagem concluido");
            nomeImagem = fileService.salvar(form.getNomeImagem(), form.getImagem());
        } catch (IOException e) {
            e.printStackTrace();
            Error error = new Error("409");
            LOG.error("Erro na imagen upload");
            return Response.status(Status.CONFLICT).entity(error).build();
        }

        CapinhaResponseDTO capinhaDTO = service.findById(null);
        capinhaDTO = service.updateNomeImagem(capinhaDTO.id(), nomeImagem);

        return Response.ok().build();
    }

    @GET
    @Path("/download/imagem/{nomeImagem}")
    @RolesAllowed({ "User", "Admin" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImagemCapinha(@PathParam("nomeImagem") String nomeImagem) {
        try {
            LOG.info("download imagem concluido");
            LOG.debug("download imagem concluido");
            ResponseBuilder response = Response.ok(fileService.obter(nomeImagem));
            response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
            return response.build();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Erro na imagen download");
            Error error = new Error("409");
            return Response.status(Status.CONFLICT).entity(error).build();
        }
    }
}
