// src/main/java/com/example/order/application/usecases/CustomerService.java
package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.in.Customer.GetAllCustomersUseCase;
import com.example.order.application.ports.in.Customer.GetCustomerByCpfUseCase;
import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements
        GetAllCustomersUseCase,
        GetCustomerByCpfUseCase
{

    private final CustomerRepositoryPort customerRepositoryPort;

    public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public List<Customer> execute() {
        return customerRepositoryPort.findAll();
    }

    @Override
    public Optional<Customer> execute(String cpf) {
        return customerRepositoryPort.findByCpf(cpf);
    }


}