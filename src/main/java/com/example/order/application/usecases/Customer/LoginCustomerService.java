package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.in.Customer.LoginCustomerUseCase;
import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginCustomerService implements LoginCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public LoginCustomerService(CustomerRepositoryPort customerRepositoryPort, PasswordEncoder passwordEncoder) {
        this.customerRepositoryPort = customerRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer execute(String cpf, String senhaPura) {
        // 1. Procura o cliente através do CPF
        Customer customer = customerRepositoryPort.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas.")); // Evitar dizer "CPF não encontrado" por motivos de segurança

        // 2. Compara a palavra-passe enviada em texto limpo com o hash guardado na base de dados
        boolean senhaValida = passwordEncoder.matches(senhaPura, customer.getSenha());

        if (!senhaValida) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        // 3. Se tudo estiver correto, devolve o cliente (numa aplicação real, gerar-se-ia um token JWT aqui)
        return customer;
    }
}