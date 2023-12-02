package com.casefy.service;

import java.util.List;

import com.casefy.dto.ClienteDTO;
import com.casefy.dto.ClienteResponseDTO;

public interface ClienteService {

   public ClienteResponseDTO insert(ClienteDTO dto) throws Exception;

    public ClienteResponseDTO update(ClienteDTO dto, Long id);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public List<ClienteResponseDTO> findByNome(String nome);

    public List<ClienteResponseDTO> findByAll(); 
    
}
