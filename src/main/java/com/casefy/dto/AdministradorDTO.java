package com.casefy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AdministradorDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        @NotBlank(message = "O campo email não pode ser nulo.") @Email(message = "O campo email não esta no formato correto!") String email,
        @NotBlank(message = "O campo senha não pode ser nulo.") String senha,
        @NotBlank(message = "O campo CPF não pode ser nulo.") @Pattern(regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$", message = "CPF inválido")String cpf,
        @NotNull(message = "O campo Matricula não pode ser nulo.") Integer matricula,
        @NotNull(message = "O campo Perfil não pode ser nulo.") Integer idPerfil) {
}
