package com.casefy.dto;

import java.util.List;

import com.casefy.model.ItemVenda;

public record ItemVendaResponseDTO(
        Integer quantidade,
        Double valor,
        Long idCapinha,
        String nome
) {
    public static ItemVendaResponseDTO valueOf(ItemVenda item) {
        return new ItemVendaResponseDTO(
            item.getQuantidade(),
            item.getValor(),
            item.getCapinha().getId(),
            item.getCapinha().getNome()
        );
    }

    public static List<ItemVendaResponseDTO> valueOf(List<ItemVenda> item) {
        return item.stream().map(i -> ItemVendaResponseDTO.valueOf(i)).toList();
    }
}
