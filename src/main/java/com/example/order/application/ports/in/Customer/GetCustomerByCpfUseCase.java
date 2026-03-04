// src/main/java/com/example/order/application/ports/in/GetCustomerByCpfUseCase.java
package com.example.order.application.ports.in.Customer;

import com.example.order.domain.entities.Customer;
import java.util.Optional;

public interface GetCustomerByCpfUseCase {
    Optional<Customer> execute(String cpf);
}