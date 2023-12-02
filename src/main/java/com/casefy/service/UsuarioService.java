package com.casefy.service;

import java.util.List;

import com.casefy.dto.UsuarioDTO;
import com.casefy.dto.UsuarioResponseDTO;

import jakarta.validation.Valid;

public interface UsuarioService {

    public UsuarioResponseDTO insert(@Valid UsuarioDTO dto);

    public UsuarioResponseDTO update(UsuarioDTO dto, Long id);

    public void delete(Long id);

    public UsuarioResponseDTO findById(Long id);

    public List<UsuarioResponseDTO> findByNome(String nome);

    public UsuarioResponseDTO findByLoginAndSenha(String login, String senha);

    public UsuarioResponseDTO findByLogin(String login);

    public List<UsuarioResponseDTO> findByAll(); 

    public UsuarioResponseDTO updateNome( String login, String nome);

    public UsuarioResponseDTO updateSenha( String login, String senhaString);

    public UsuarioResponseDTO updateLogin( String login, String novoLogin);

    public UsuarioResponseDTO updateCPF( String login, String cpf);

    
}