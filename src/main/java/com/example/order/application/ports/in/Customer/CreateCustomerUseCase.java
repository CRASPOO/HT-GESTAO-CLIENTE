// src/main/java/com/example/order/application/ports/in/CreateCustomerUseCase.java
package com.example.order.application.ports.in.Customer;

import com.example.order.domain.entities.Customer;

public interface CreateCustomerUseCase {

    Customer execute(Customer customer);
}