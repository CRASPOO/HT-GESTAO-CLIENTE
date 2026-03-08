package com.example.order.domain.entities;

public class Customer {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String senha;

    // Construtor vazio (necessário para instanciar no Controller)
    public Customer() {
    }

    // Construtor completo (necessário para o JpaEntity converter para Domínio)
    public Customer(Long id, String name, String email, String cpf, String senha) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}