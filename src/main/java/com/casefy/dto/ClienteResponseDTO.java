package com.casefy.dto;

import java.util.List;

import com.casefy.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String login,
    List<TelefoneDTO> listaTelefone
) { 
    public static ClienteResponseDTO valueOf(Cliente cliente){
        if (cliente == null) {
            return new ClienteResponseDTO(null, null, null, null);
        }
        return new ClienteResponseDTO(
            cliente.getId(), 
            cliente.getNome(),
            cliente.getLogin(),
            cliente.getTelefone()
                .stream()
                .map(t -> TelefoneDTO.valueOf(t)).toList()
            
        );
    }
}
