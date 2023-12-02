package com.casefy.dto;

import java.math.BigDecimal;

import com.casefy.model.Capinha;
import com.casefy.model.Modelo;
import com.casefy.model.Cor;

public record CapinhaResponseDTO(
        Long id,
        Modelo modelo,
        Cor cor,
        String nome,
        BigDecimal valor,
        Integer quantEstoque,
        String nomeImagem,
        String descricao) {
    public static CapinhaResponseDTO valueOf(Capinha capinha) {
        return new CapinhaResponseDTO(capinha.getId(),
                capinha.getModelo(),
                capinha.getCor(),
                capinha.getNome(),
                capinha.getValor(),
                capinha.getQuantEstoque(),
                capinha.getNomeImagem(),
                capinha.getDescricao());
    }
}
