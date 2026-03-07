package com.example.order.application.ports.in.Customer;

import com.example.order.domain.entities.Customer;

public interface LoginCustomerUseCase {
    Customer execute(String cpf, String senha);
}