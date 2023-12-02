package com.casefy.service;

import java.util.List;

import com.casefy.dto.PixDTO;
import com.casefy.dto.PixResponseDTO;

public interface PixService {
    public PixResponseDTO insert(PixDTO dto);

    public PixResponseDTO update(PixDTO dto, Long id);

    public void delete(Long id);

    public PixResponseDTO findById(Long id);

    public List<PixResponseDTO> findByChave(String numero);

    public List<PixResponseDTO> findByAll(); 
}
