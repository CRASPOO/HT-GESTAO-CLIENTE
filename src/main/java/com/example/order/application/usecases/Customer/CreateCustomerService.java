// src/main/java/com/example/order/application/usecases/CreateCustomerService.java
package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.in.Customer.CreateCustomerUseCase;
import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;

@Service
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;
    private final PasswordEncoder passwordEncoder; // <-- Nova dependência para criptografia

    // Injetando o PasswordEncoder no construtor junto com o repositório
    public CreateCustomerService(CustomerRepositoryPort customerRepositoryPort, PasswordEncoder passwordEncoder) {
        this.customerRepositoryPort = customerRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Customer execute(Customer customer) {
        // Lógica de negócio: verifica se o CPF já existe
        if (customerRepositoryPort.findByCpf(customer.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Customer with this CPF already exists.");
        }

        // 1. Pega a senha em texto puro que veio da requisição
        String senhaTextoPuro = customer.getSenha();

        // 2. Gera o hash usando o BCrypt (através da interface PasswordEncoder)
        String senhaCriptografada = passwordEncoder.encode(senhaTextoPuro);

        // 3. Atualiza a entidade com a senha criptografada antes de salvar
        customer.setSenha(senhaCriptografada); // ou setSenha()

        // 4. Salva a entidade de forma segura
        return customerRepositoryPort.save(customer);
    }
}