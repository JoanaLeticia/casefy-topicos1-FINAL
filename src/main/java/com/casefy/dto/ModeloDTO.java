package com.casefy.dto;

import jakarta.validation.constraints.NotBlank;

public record ModeloDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        @NotBlank(message = "O campo marca não pode ser nulo.") String marca) {

}
