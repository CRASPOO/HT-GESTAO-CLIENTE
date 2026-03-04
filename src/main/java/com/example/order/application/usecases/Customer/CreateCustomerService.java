// src/main/java/com/example/order/application/usecases/CreateCustomerService.java
package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.in.Customer.CreateCustomerUseCase;
import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CreateCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    @Transactional
    public Customer execute(Customer customer) {
        // Lógica de negócio: verifica se o CPF já existe
        if (customerRepositoryPort.findByCpf(customer.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Customer with this CPF already exists.");
        }
        return customerRepositoryPort.save(customer);
    }
}