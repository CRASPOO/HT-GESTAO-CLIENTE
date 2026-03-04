package com.example.order.infrastructure.adapters.out.persistence.JpaAdapter;

import com.example.order.domain.entities.Customer;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.CustomerJpaEntity;
import com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa.SpringDataJpaCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerJpaAdapterTest {

    @Mock
    private SpringDataJpaCustomerRepository springDataRepository;

    @InjectMocks
    private CustomerJpaAdapter customerJpaAdapter;

    @Test
    @DisplayName("Deve retornar lista de Customers (Domínio) ao buscar todos")
    void deveRetornarTodos() {
        // Arrange
        CustomerJpaEntity entity1 = new CustomerJpaEntity(1L, "Maria", "maria@test.com", "111");
        CustomerJpaEntity entity2 = new CustomerJpaEntity(2L, "Joao", "joao@test.com", "222");

        when(springDataRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        // Act
        List<Customer> resultado = customerJpaAdapter.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Maria", resultado.get(0).getName());
        assertEquals("Joao", resultado.get(1).getName());

        verify(springDataRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar Customer (Domínio) quando encontrar por CPF")
    void deveRetornarPorCpfQuandoEncontrado() {
        // Arrange
        String cpf = "12345678900";
        CustomerJpaEntity entity = new CustomerJpaEntity(1L, "Carlos", "carlos@test.com", cpf);

        when(springDataRepository.findByCpf(cpf)).thenReturn(Optional.of(entity));

        // Act
        Optional<Customer> resultado = customerJpaAdapter.findByCpf(cpf);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getName());
        assertEquals(cpf, resultado.get().getCpf());
    }

    @Test
    @DisplayName("Deve retornar Vazio quando não encontrar por CPF")
    void deveRetornarVazioPorCpf() {
        // Arrange
        String cpf = "999";
        when(springDataRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> resultado = customerJpaAdapter.findByCpf(cpf);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve retornar Customer (Domínio) quando encontrar por ID")
    void deveRetornarPorIdQuandoEncontrado() {
        // Arrange
        Long id = 10L;
        CustomerJpaEntity entity = new CustomerJpaEntity(id, "Ana", "ana@test.com", "111");

        when(springDataRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Optional<Customer> resultado = customerJpaAdapter.findById(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
    }

    @Test
    @DisplayName("Deve salvar e converter para Domínio corretamente")
    void deveSalvarCustomer() {
        // Arrange
        // Objeto de entrada (Domínio)
        Customer customerDomain = new Customer();
        customerDomain.setName("Novo Cliente");
        customerDomain.setEmail("novo@test.com");
        customerDomain.setCpf("12345678900");

        // Objeto simulado que o banco devolveria (com ID gerado)
        CustomerJpaEntity savedEntity = new CustomerJpaEntity(50L, "Novo Cliente", "novo@test.com", "12345678900");

        when(springDataRepository.save(any(CustomerJpaEntity.class))).thenReturn(savedEntity);

        // Act
        Customer resultado = customerJpaAdapter.save(customerDomain);

        // Assert
        assertNotNull(resultado);
        assertEquals(50L, resultado.getId()); // Verifica se o ID voltou preenchido
        assertEquals("Novo Cliente", resultado.getName());

        // Verifica se o Adapter chamou o save do repositório
        verify(springDataRepository, times(1)).save(any(CustomerJpaEntity.class));
    }
}