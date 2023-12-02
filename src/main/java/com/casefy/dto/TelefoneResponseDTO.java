package com.casefy.dto;

import com.casefy.model.Telefone;

public record TelefoneResponseDTO(
        Long id,
        String codigoArea,
        String numero) {
    public static TelefoneResponseDTO valueOf(Telefone telefone) {
        return new TelefoneResponseDTO(
                telefone.getId(),
                telefone.getCodArea(),
                telefone.getNumero());
    }
}