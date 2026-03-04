// src/main/java/com/example/order/application/ports/in/GetAllCustomersUseCase.java
package com.example.order.application.ports.in.Customer;

import com.example.order.domain.entities.Customer;
import java.util.List;

public interface GetAllCustomersUseCase {
    List<Customer> execute();
}