package com.casefy.dto;

import com.casefy.model.Modelo;

public record ModeloResponseDTO(
    Long id,
    String nome,
    String marca) {
    public static ModeloResponseDTO valueOf(Modelo modelo) {
        return new ModeloResponseDTO(
            modelo.getId(),
            modelo.getNome(),
            modelo.getMarca());
    }
}
