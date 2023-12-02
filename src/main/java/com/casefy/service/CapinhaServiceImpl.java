package com.casefy.service;

import java.util.List;

import com.casefy.dto.CapinhaDTO;
import com.casefy.dto.CapinhaResponseDTO;
import com.casefy.model.Capinha;
import com.casefy.repository.CapinhaRepository;
import com.casefy.repository.ModeloRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CapinhaServiceImpl implements CapinhaService {
    @Inject
    CapinhaRepository repository;

    @Inject
    ModeloRepository modeloRepository;


    @Override
    @Transactional
    public CapinhaResponseDTO insert(CapinhaDTO dto) {

        Capinha novaCapinha = new Capinha();
        novaCapinha.setModelo(modeloRepository.findById(dto.idModelo()));
        novaCapinha.setCor(dto.cor());
        novaCapinha.setNome(dto.nome());
        novaCapinha.setValor(dto.valor());
        novaCapinha.setQuantEstoque(dto.quantEstoque());
        novaCapinha.setDescricao(dto.descricao());

        repository.persist(novaCapinha);

        return CapinhaResponseDTO.valueOf(novaCapinha);
    }

    @Override
    @Transactional
    public CapinhaResponseDTO update(CapinhaDTO dto, Long id) {

        Capinha capinhaExistente = repository.findById(id);

        capinhaExistente.setNome(dto.nome());
        capinhaExistente.setCor(dto.cor());
        capinhaExistente.setDescricao(dto.descricao());
        capinhaExistente.setModelo(modeloRepository.findById(dto.idModelo()));
        capinhaExistente.setQuantEstoque(dto.quantEstoque());
        capinhaExistente.setValor(dto.valor());

        return CapinhaResponseDTO.valueOf(capinhaExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public CapinhaResponseDTO findById(Long id) {
        Capinha capinha = repository.findById(id);
        if (capinha == null) {
            throw new EntityNotFoundException("Capinha não encontrada com ID: " + id);
        }
        return CapinhaResponseDTO.valueOf(capinha);
    }

    @Override
    public List<CapinhaResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> CapinhaResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<CapinhaResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> CapinhaResponseDTO.valueOf(e)).toList();
    }

    @Override
    public CapinhaResponseDTO updateNomeImagem(Long id, String nomeImagem) {
        Capinha capinha = repository.findById(id);
        capinha.setNomeImagem(nomeImagem);
        return CapinhaResponseDTO.valueOf(capinha);}
}
