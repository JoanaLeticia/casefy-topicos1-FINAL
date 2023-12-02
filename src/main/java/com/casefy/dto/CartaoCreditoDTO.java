package com.casefy.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CartaoCreditoDTO(
     @NotBlank(message = "O campo bandeira não pode ser nulo.") String bandeira,
     @NotBlank(message = "O campo numero do cartão não pode ser nulo.") String numeroCartao,
     @NotBlank(message = "O campo codigo de seguranca não pode ser nulo.") String codigoSeguranca,
     LocalDate dataVencimento){}

