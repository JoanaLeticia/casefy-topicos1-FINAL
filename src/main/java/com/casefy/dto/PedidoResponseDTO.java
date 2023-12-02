package com.casefy.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.casefy.model.Pedido;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime horario,
        UsuarioResponseDTO usuario,
        Double valorTotal,
        List<ItemVendaResponseDTO> itens) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataHora(),
                UsuarioResponseDTO.valueOf(pedido.getCliente()),
                pedido.getValorTotal(),
                ItemVendaResponseDTO.valueOf(pedido.getItens()));
    }
}
