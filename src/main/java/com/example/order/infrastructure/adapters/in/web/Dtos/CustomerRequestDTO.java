// src/main/java/com/example/order/infrastructure/adapters/in/web/dtos/CustomerRequestDTO.java
package com.example.order.infrastructure.adapters.in.web.Dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(
        Long id,
        String name,
        String email,
        String cpf,
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String senha // <-- NOVA PROPRIEDADE AQUI
) {}