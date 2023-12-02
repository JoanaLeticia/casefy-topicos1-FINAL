package com.casefy.dto;

import com.casefy.model.Pagamento;

public record PagamentoResponseDTO(
        Long id,
        String tipo,
        Float valor) {
    public static PagamentoResponseDTO valueOf(Pagamento pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getTipo(),
                pagamento.getValor());
    }
}