package com.casefy.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import com.casefy.dto.UsuarioDTO;
import com.casefy.repository.UsuarioRepository;
import com.casefy.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuariologado")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioRepository repository;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response getUsuario() {
        // obtendo o login pelo token jwt
        String login = jwt.getSubject();
        LOG.info("obtendo o login pelo token jwt");
        LOG.info("Retornando login");
        return Response.ok(usuarioService.findByLogin(login)).build();
    }

    @PATCH
    @Transactional
    @Path("/updateNome/{nome}")
    @RolesAllowed({ "User", "Admin" })
    public Response updateNome( @PathParam("nome") String nome) {
        String login = jwt.getSubject();
        try {
            usuarioService.updateNome( login, nome);
            LOG.info("Nome atualizado!");
            return Response.ok("Informações do usuário atualizadas com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar informações do usuário: " + e.getMessage())
                    .build();
        }
    }

    @PATCH
@Transactional
@Path("/updateSenha/{senha}")
@RolesAllowed({ "User", "Admin" })
public Response updateSenha( @PathParam("senha") String senha) {
    String login = jwt.getSubject();
    try {
        usuarioService.updateSenha( login, senha);
        LOG.info("Senha atualizada!");
        return Response.ok("Informações do usuário atualizadas com sucesso").build();
    } catch (Exception e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao atualizar informações do usuário: " + e.getMessage())
                .build();
    }
}

@PATCH
@Transactional
@Path("/updateLogin/{login}")
@RolesAllowed({ "User", "Admin" })
public Response updateLogin( @PathParam("login") String novoLogin) {
    String login = jwt.getSubject();
    try {
        usuarioService.updateLogin( login, novoLogin);
        LOG.info("Login atualizado!");
        return Response.ok("Informações do usuário atualizadas com sucesso").build();
    } catch (Exception e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao atualizar informações do usuário: " + e.getMessage())
                .build();
    }
}

@PATCH
@Transactional
@Path("/updateCPF/{cpf}")
@RolesAllowed({ "User", "Admin" })
public Response updateCPF( @PathParam("cpf") String cpf) {
    String login = jwt.getSubject();
    try {
        usuarioService.updateCPF( login, cpf);
        LOG.info("CPF atualizado!");
        return Response.ok("Informações do usuário atualizadas com sucesso").build();
    } catch (Exception e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao atualizar informações do usuário: " + e.getMessage())
                .build();
    }
}
}