package com.casefy.service;

import com.casefy.dto.CidadeDTO;
import com.casefy.dto.CidadeResponseDTO;
import java.util.List;

public interface CidadeService {
    public CidadeResponseDTO insert(CidadeDTO dto);

    public CidadeResponseDTO update(CidadeDTO dto, Long id);

    public void delete(Long id);

    public CidadeResponseDTO findById(Long id);

    public List<CidadeResponseDTO> findByNome(String nome);

    public List<CidadeResponseDTO> findByAll(); 
}
