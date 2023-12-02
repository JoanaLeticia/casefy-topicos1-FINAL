package com.casefy.service;

import java.util.List;
import java.util.stream.Collectors;

import com.casefy.dto.PixDTO;
import com.casefy.dto.PixResponseDTO;
import com.casefy.model.Pix;
import com.casefy.repository.PixRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PixServiceImpl implements PixService {

    @Inject
    PixRepository repository;

    @Override
    @Transactional
    public PixResponseDTO insert(PixDTO dto) {
        Pix novoPix = new Pix();
        novoPix.setChavePix(dto.chavePix());
        repository.persist(novoPix);
        return PixResponseDTO.valueOf(novoPix);
    }

    @Override
    @Transactional
    public PixResponseDTO update(PixDTO dto, Long id) {
        Pix pixExistente = repository.findById(id);
        pixExistente.setChavePix(dto.chavePix());
        return PixResponseDTO.valueOf(pixExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public PixResponseDTO findById(Long id) {
        Pix boleto = repository.findById(id);
        if (boleto == null) {
            throw new EntityNotFoundException("Pix não encontrado com ID: " + id);
        }
        return PixResponseDTO.valueOf(boleto);
    }

    @Override
    public List<PixResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> PixResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PixResponseDTO> findByChave(String chave) {
         List<Pix> list = repository.findByChave(chave);
        return list.stream().map(u -> PixResponseDTO.valueOf(u)).collect(Collectors.toList());
    
    }

}