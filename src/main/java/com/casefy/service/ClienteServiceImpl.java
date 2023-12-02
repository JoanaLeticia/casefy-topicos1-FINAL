package com.casefy.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.casefy.dto.ClienteDTO;
import com.casefy.dto.ClienteResponseDTO;
import com.casefy.model.Cliente;
import com.casefy.model.Telefone;
import com.casefy.repository.ClienteRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository repository;

    @Override
    @Transactional
    public ClienteResponseDTO insert(ClienteDTO dto) throws Exception {
        // Verifique se o email já existe
        if (repository.findByEmail(dto.login()) != null) {
            throw new Exception("Login já existe.");
        }

        // Crie um novo cliente
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(dto.nome());
        novoCliente.setCpf(dto.cpf());
        novoCliente.setLogin(dto.login());
        novoCliente.setSenha(dto.senha());
        novoCliente.setDataNascimento(dto.dataNascimento());

        // Configure a lista de telefones
        if (dto.listaTelefone() != null && !dto.listaTelefone().isEmpty()) {
            List<Telefone> telefones = dto.listaTelefone().stream()
                    .map(tel -> {
                        Telefone telefone = new Telefone();
                        telefone.setCodArea(tel.codigoArea());
                        telefone.setNumero(tel.numero());
                        return telefone;
                    })
                    .collect(Collectors.toList());
            novoCliente.setTelefone(telefones);
        } else {
            novoCliente.setTelefone(Collections.emptyList());
        }

        /*  Configure a lista de endereços
        if (dto.listaEndereco() != null && !dto.listaEndereco().isEmpty()) {
            List<Endereco> enderecos = dto.listaEndereco().stream()
                    .map(end -> {
                        Endereco endereco = new Endereco();
                        endereco.setCep(end.cep());
                        endereco.setBairro(end.bairro());
                        endereco.setNumero(end.numero());
                        endereco.setLogradouro(end.logradouro());
                        endereco.setComplemento(end.complemento());
                        endereco.setCidade(end.cidade());

                        return endereco;
                    })
                    .collect(Collectors.toList());
            novoCliente.setEndereco(enderecos);
        } else {
            novoCliente.setEndereco(Collections.emptyList());
        } */

        // Persista o novo cliente
        repository.persist(novoCliente);

        return ClienteResponseDTO.valueOf(novoCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(ClienteDTO dto, Long id) {

        Cliente clienteExistente = repository.findById(id);

        // Atualize os campos do cliente com base no DTO
        clienteExistente.setNome(dto.nome());
        clienteExistente.setCpf(dto.cpf());
        clienteExistente.setLogin(dto.login());
        clienteExistente.setSenha(dto.senha());
        clienteExistente.setDataNascimento(dto.dataNascimento());

        // Atualize a lista de telefones
        if (dto.listaTelefone() != null && !dto.listaTelefone().isEmpty()) {
            clienteExistente.getTelefone().clear();
            List<Telefone> telefones = dto.listaTelefone().stream()
                    .map(tel -> {
                        Telefone telefone = new Telefone();
                        telefone.setCodArea(tel.codigoArea());
                        telefone.setNumero(tel.numero());
                        return telefone;
                    })
                    .collect(Collectors.toList());
            clienteExistente.getTelefone().addAll(telefones);
        } else {
            clienteExistente.getTelefone().clear();
        }

        /* Atualize a lista de endereços
        if (dto.listaEndereco() != null && !dto.listaEndereco().isEmpty()) {
            clienteExistente.getEndereco().clear();
            List<Endereco> enderecos = dto.listaEndereco().stream()
                    .map(end -> {
                        Endereco endereco = new Endereco();
                        endereco.setCep(end.cep());
                        endereco.setBairro(end.bairro());
                        endereco.setNumero(end.numero());
                        endereco.setLogradouro(end.logradouro());
                        endereco.setComplemento(end.complemento());
                        endereco.setCidade(end.cidade());

                        return endereco;
                    })
                    .collect(Collectors.toList());
            clienteExistente.getEndereco().addAll(enderecos);
        } else {
            clienteExistente.getEndereco().clear();
        } */

        return ClienteResponseDTO.valueOf(clienteExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new EntityNotFoundException("Administrador não encontrado com ID: " + id);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ClienteResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

}