package com.casefy.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.casefy.dto.EstadoDTO;
import com.casefy.dto.EstadoResponseDTO;
import com.casefy.model.Estado;
import com.casefy.repository.EstadoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {
    @Inject
    EstadoRepository repository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public EstadoResponseDTO insert(EstadoDTO dto) {
        validar(dto);
        Estado novoEstado = new Estado();
        novoEstado.setNome(dto.nome());
        novoEstado.setSigla(dto.sigla());
        repository.persist(novoEstado);
        return EstadoResponseDTO.valueOf(novoEstado);
    }

    private void validar(EstadoDTO estadoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EstadoDTO>> violations = validator.validate(estadoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update(EstadoDTO dto, Long id) throws ConstraintViolationException{
        validar(dto);
        Estado estadoExistente = repository.findById(id);
        estadoExistente.setNome(dto.nome());
        estadoExistente.setSigla(dto.sigla());
        return EstadoResponseDTO.valueOf(estadoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public EstadoResponseDTO findById(Long id) {
        Estado estado = repository.findById(id);
        if (estado == null) {
            throw new EntityNotFoundException("Estado não encontrado com ID: " + id);
        }
        return  EstadoResponseDTO.valueOf(estado);
    }

    @Override
    public List<EstadoResponseDTO> findByNome(String nome){
        List<Estado> list = repository.findByNome(nome);
        return list.stream().map(u -> EstadoResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    @Override
    public List<EstadoResponseDTO> findByAll() {
        return repository.listAll().stream().map(u -> EstadoResponseDTO.valueOf(u)).toList();
    }

}