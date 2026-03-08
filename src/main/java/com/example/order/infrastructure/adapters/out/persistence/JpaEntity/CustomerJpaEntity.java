// src/main/java/com/example/order/infrastructure/adapters/out/persistence/CustomerJpaEntity.java
package com.example.order.infrastructure.adapters.out.persistence.JpaEntity;

import com.example.order.domain.entities.Customer;
import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class CustomerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String cpf;

    @Column(nullable = false)
    private String senha; // <-- NOVA COLUNA NO BANCO

    public CustomerJpaEntity() {}

    // Construtor atualizado com a senha
    public CustomerJpaEntity(Long id, String name, String email, String cpf, String senha) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Customer toDomain() {
        // Atualizado para passar a senha na criação do Domínio
        return new Customer(this.id, this.name, this.email, this.cpf, this.senha);
    }

    public static CustomerJpaEntity fromDomain(Customer domain) {
        // Atualizado para pegar a senha do Domínio e colocar na entidade do banco
        return new CustomerJpaEntity(domain.getId(), domain.getName(), domain.getEmail(), domain.getCpf(), domain.getSenha());
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSenha() { return senha; } // <-- NOVO GETTER
    public void setSenha(String senha) { this.senha = senha; } // <-- NOVO SETTER
}