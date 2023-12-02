package com.casefy.dto;

import java.math.BigDecimal;

import com.casefy.model.Cor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CapinhaDTO(
                Long idModelo,
                Cor cor,
                @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
                @NotNull(message = "O campo valor não pode ser nulo.") BigDecimal valor,
                @NotNull(message = "O campo quantidade em estoque não pode ser nulo.") Integer quantEstoque,
                @NotBlank(message = "O campo descrição não pode ser nulo.") String descricao) {

}
