package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @InjectMocks
    private CustomerService customerService;

    // --- Testes para GetAllCustomersUseCase (execute() sem parâmetros) ---

    @Test
    @DisplayName("Deve retornar lista de clientes quando existirem registros")
    void deveRetornarListaDeClientes() {
        // Arrange
        Customer c1 = new Customer();
        c1.setName("Ana");
        Customer c2 = new Customer();
        c2.setName("Bruno");
        List<Customer> listaMock = Arrays.asList(c1, c2);

        when(customerRepositoryPort.findAll()).thenReturn(listaMock);

        // Act
        List<Customer> resultado = customerService.execute();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ana", resultado.get(0).getName());

        // Verifica se o repositório foi chamado
        verify(customerRepositoryPort, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver registros")
    void deveRetornarListaVazia() {
        // Arrange
        when(customerRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Customer> resultado = customerService.execute();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(customerRepositoryPort, times(1)).findAll();
    }

    // --- Testes para GetCustomerByCpfUseCase (execute(Long cpf)) ---

    @Test
    @DisplayName("Deve retornar cliente quando CPF existir")
    void deveRetornarClientePorCpf() {
        // Arrange
        String cpf = "12345678900";
        Customer customerMock = new Customer();
        customerMock.setCpf(cpf);
        customerMock.setName("Carlos");

        when(customerRepositoryPort.findByCpf(cpf)).thenReturn(Optional.of(customerMock));

        // Act
        Optional<Customer> resultado = customerService.execute(cpf);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(cpf, resultado.get().getCpf());
        assertEquals("Carlos", resultado.get().getName());

        verify(customerRepositoryPort, times(1)).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando CPF não existir")
    void deveRetornarVazioQuandoCpfNaoEncontrado() {
        // Arrange
        String cpfInexistente = "99999999999";
        when(customerRepositoryPort.findByCpf(cpfInexistente)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> resultado = customerService.execute(cpfInexistente);

        // Assert
        assertFalse(resultado.isPresent()); // Garante que veio vazio

        verify(customerRepositoryPort, times(1)).findByCpf(cpfInexistente);
    }
}