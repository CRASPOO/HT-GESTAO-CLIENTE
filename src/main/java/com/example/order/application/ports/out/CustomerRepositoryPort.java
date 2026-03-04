// src/main/java/com/example/order/application/ports/out/CustomerRepositoryPort.java
package com.example.order.application.ports.out;

import com.example.order.domain.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    List<Customer> findAll();
    Optional<Customer> findByCpf(String cpf);
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
}