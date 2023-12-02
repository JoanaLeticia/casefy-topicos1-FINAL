package com.casefy.service;

import java.util.List;

import com.casefy.dto.ModeloDTO;
import com.casefy.dto.ModeloResponseDTO;

public interface ModeloService {
    public ModeloResponseDTO insert(ModeloDTO dto);

    public ModeloResponseDTO update(ModeloDTO dto, Long id);

    public void delete(Long id);

    public ModeloResponseDTO findById(Long id);

    public List<ModeloResponseDTO> findByNome(String nome);

    public List<ModeloResponseDTO> findByAll();
}
