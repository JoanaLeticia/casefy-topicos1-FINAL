package com.casefy.service;

import java.util.List;

import com.casefy.dto.UsuarioDTO;
import com.casefy.dto.UsuarioResponseDTO;
import com.casefy.model.Perfil;
import com.casefy.model.Usuario;
import com.casefy.repository.UsuarioRepository;
import com.casefy.validation.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Override
    @Transactional
    public UsuarioResponseDTO insert(@Valid UsuarioDTO dto) {

        if (repository.findByLogin(dto.login()) != null) {
            throw new ValidationException("login", "Login já existe.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setLogin(dto.login());
        novoUsuario.setSenha(dto.senha());
        novoUsuario.setCpf(dto.cpf());
        novoUsuario.setPerfil(null);
        novoUsuario.setPerfil(Perfil.valueOf(dto.idPerfil()));

        repository.persist(novoUsuario);

        return UsuarioResponseDTO.valueOf(novoUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(UsuarioDTO dto, Long id) {
        Usuario usuario = repository.findById(id);
        usuario.setLogin(dto.login());
        usuario.setNome(dto.nome());
        usuario.setSenha(dto.senha());
        usuario.setCpf(dto.cpf());
        usuario.setPerfil(Perfil.valueOf(dto.idPerfil()));

        repository.persist(usuario);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        return UsuarioResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
        return null;
    }

    @Override
    public List<UsuarioResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UsuarioResponseDTO findByLoginAndSenha(String login, String senha) {
        Usuario usuario = repository.findByLoginAndSenha(login, senha);
        if (usuario == null)
            throw new ValidationException("login", "Login ou senha inválido");

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null)
            throw new ValidationException("login", "Login inválido");

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO updateNome(String login, String nome) {
        Usuario usuario = repository.findByLogin(login);
        usuario.setNome(nome);
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO updateSenha(String login, String senha) {
        Usuario usuario = repository.findByLogin(login);
        usuario.setSenha(senha);
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO updateLogin(String login, String novoLogin) {
        Usuario usuario = repository.findByLogin(login);
        usuario.setLogin(novoLogin);
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO updateCPF(String login, String cpf) {
        Usuario usuario = repository.findByLogin(login);
        usuario.setCpf(cpf);
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

}