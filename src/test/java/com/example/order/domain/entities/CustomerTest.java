// java
package com.example.order.domain.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarClienteValido() {
        Customer customer = new Customer();
        customer.setName("Maria Silva");
        customer.setEmail("maria.silva@example.com");
        customer.setCpf("12345678900");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.isEmpty(), "Não deve haver violações para cliente válido");
    }

    @Test
    void deveDetectarNomeEmBranco() {
        Customer customer = new Customer();
        customer.setName("  ");
        customer.setEmail("maria.silva@example.com");
        customer.setCpf("12345678900");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty(), "Deve haver violações quando o nome estiver em branco");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void deveDetectarEmailInvalido() {
        Customer customer = new Customer();
        customer.setName("Maria Silva");
        customer.setEmail("email-invalido");
        customer.setCpf("12345678900");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty(), "Deve haver violações para email inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void deveDetectarCpfComTamanhoIncorreto() {
        Customer customer = new Customer();
        customer.setName("Maria Silva");
        customer.setEmail("maria.silva@example.com");
        customer.setCpf("12345"); // cpf inválido

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty(), "Deve haver violações para CPF com tamanho incorreto");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")));
    }
}
