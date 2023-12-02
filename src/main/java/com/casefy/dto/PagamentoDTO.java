package com.casefy.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PagamentoDTO(
        @NotBlank(message = "O campo tipo não pode ser nulo") String tipo,
        @NotNull(message = "O campo valor não pode ser nulo") Float valor,
        List<PixDTO> pix,
        List<BoletoBancarioDTO> boletoBancario,
        List<CartaoCreditoDTO> cartaoCredito) {
}