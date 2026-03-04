// src/main/java/com/example/order/infrastructure/adapters/in/web/dtos/CustomerResponseDTO.java
package com.example.order.infrastructure.adapters.in.web.Dtos;

import com.example.order.domain.entities.Customer;

public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String cpf;

    public CustomerResponseDTO() {}

    public CustomerResponseDTO(Long id, String name, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    public static CustomerResponseDTO fromDomain(Customer customer) {
        return new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getCpf());
    }

    // Getters e setters adicionados para serialização
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}