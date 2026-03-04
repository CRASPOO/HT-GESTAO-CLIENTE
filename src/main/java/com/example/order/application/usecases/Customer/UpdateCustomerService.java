// src/main/java/com/example/order/application/usecases/UpdateCustomerService.java
package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.in.Customer.UpdateCustomerUseCase;
import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import com.example.order.shared.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public UpdateCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    @Transactional
    public Customer execute(Customer customer) {
        // Lógica de negócio: verifica se o cliente a ser atualizado existe
        customerRepositoryPort.findById(customer.getId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customer.getId()));
        return customerRepositoryPort.save(customer);
    }
}