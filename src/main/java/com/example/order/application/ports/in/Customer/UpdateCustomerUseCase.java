// src/main/java/com/example/order/application/ports/in/UpdateCustomerUseCase.java
package com.example.order.application.ports.in.Customer;

import com.example.order.domain.entities.Customer;

public interface UpdateCustomerUseCase {
    Customer execute(Customer customer);
}