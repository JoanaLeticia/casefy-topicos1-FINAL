package com.casefy.service;

import java.util.List;

import com.casefy.dto.AdministradorDTO;
import com.casefy.dto.AdministradorResponseDTO;

public interface AdministradorService {

   public AdministradorResponseDTO insert(AdministradorDTO dto);

    public AdministradorResponseDTO update(AdministradorDTO dto, Long id);

    public void delete(Long id);

    public AdministradorResponseDTO findById(Long id);

    public List<AdministradorResponseDTO> findByNome(String nome);

    public List<AdministradorResponseDTO> findByAll(); 
    
}