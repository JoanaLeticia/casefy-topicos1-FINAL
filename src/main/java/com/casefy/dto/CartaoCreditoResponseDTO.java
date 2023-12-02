package com.casefy.dto;

import com.casefy.model.CartaoCredito;

public record CartaoCreditoResponseDTO(
        Long id,
        String bandeira,
        String numeroCartao) {
    public static CartaoCreditoResponseDTO valueOf(CartaoCredito credito) {
        return new CartaoCreditoResponseDTO(
                credito.getId(),
                credito.getBandeira(),
                credito.getNumeroCartao());
    }
}