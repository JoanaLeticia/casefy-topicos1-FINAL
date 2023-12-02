package com.casefy.dto;

import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO (
    @NotNull(message = "O campo pagamento não pode ser nulo")
    Long idPagamento,
    @NotNull(message = "O campo endereco não pode ser nulo")
    Long idEndereco,
    @NotBlank(message = "O campo itens de venda não pode ser nulo")
    List<ItemVendaDTO> itens
) {
    
}
