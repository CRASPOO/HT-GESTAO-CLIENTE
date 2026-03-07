package com.example.order.infrastructure.adapters.in.web.Dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,

        @NotBlank(message = "A palavra-passe é obrigatória")
        String senha
) {}