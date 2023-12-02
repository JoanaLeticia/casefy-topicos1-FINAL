package com.casefy.service;

import java.util.List;

import com.casefy.dto.CapinhaDTO;
import com.casefy.dto.CapinhaResponseDTO;

public interface CapinhaService {
    public CapinhaResponseDTO insert(CapinhaDTO dto);

    public CapinhaResponseDTO update(CapinhaDTO dto, Long id);

    public void delete(Long id);

    public CapinhaResponseDTO findById(Long id);

    public List<CapinhaResponseDTO> findByNome(String nome);

    public List<CapinhaResponseDTO> findByAll();

    public CapinhaResponseDTO updateNomeImagem(Long id, String nomeImagem) ;
}
