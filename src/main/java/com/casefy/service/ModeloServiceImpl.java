package com.casefy.service;

import java.util.List;

import com.casefy.dto.ModeloDTO;
import com.casefy.dto.ModeloResponseDTO;
import com.casefy.model.Modelo;
import com.casefy.repository.ModeloRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ModeloServiceImpl implements ModeloService {
    @Inject
    ModeloRepository repository;

    @Override
    @Transactional
    public ModeloResponseDTO insert(ModeloDTO dto) {

        Modelo novoModelo = new Modelo();
        novoModelo.setNome(dto.nome());
        novoModelo.setMarca(dto.marca());

        repository.persist(novoModelo);

        return ModeloResponseDTO.valueOf(novoModelo);
    }

    @Override
    @Transactional
    public ModeloResponseDTO update(ModeloDTO dto, Long id) {

        Modelo modeloExistente = repository.findById(id);
        modeloExistente.setNome(dto.nome());
        modeloExistente.setMarca(dto.marca());

        return ModeloResponseDTO.valueOf(modeloExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public ModeloResponseDTO findById(Long id) {
        Modelo modelo = repository.findById(id);
        if (modelo == null) {
            throw new EntityNotFoundException("Modelo não encontrado com ID: " + id);
        }
        return ModeloResponseDTO.valueOf(modelo);
    }

    @Override
    public List<ModeloResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(e -> ModeloResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ModeloResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> ModeloResponseDTO.valueOf(e)).toList();
    }
}
