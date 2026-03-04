// src/main/java/com/example/order/infrastructure/adapters/in/web/dtos/CustomerRequestDTO.java
package com.example.order.infrastructure.adapters.in.web.Dtos;

public record CustomerRequestDTO(Long id, String name, String email, String cpf) {}