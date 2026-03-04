package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import com.example.order.shared.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @InjectMocks
    private UpdateCustomerService updateCustomerService;

    @Test
    @DisplayName("Deve atualizar cliente com sucesso quando ID existe")
    void deveAtualizarClienteComSucesso() {
        // --- ARRANGE ---
        Long id = 1L;

        // Cliente com os dados novos que queremos salvar
        Customer customerToUpdate = new Customer();
        customerToUpdate.setId(id);
        customerToUpdate.setName("Nome Atualizado");
        customerToUpdate.setEmail("novo@email.com");

        // Cliente antigo que já está no banco (apenas para simular que o ID existe)
        Customer customerNoBanco = new Customer();
        customerNoBanco.setId(id);
        customerNoBanco.setName("Nome Antigo");

        // 1. O serviço busca pelo ID para ver se existe
        when(customerRepositoryPort.findById(id)).thenReturn(Optional.of(customerNoBanco));

        // 2. O serviço salva os dados novos
        when(customerRepositoryPort.save(customerToUpdate)).thenReturn(customerToUpdate);

        // --- ACT ---
        Customer resultado = updateCustomerService.execute(customerToUpdate);

        // --- ASSERT ---
        assertNotNull(resultado);
        assertEquals("Nome Atualizado", resultado.getName());

        // Verifica o fluxo: Buscou o ID -> Salvou
        verify(customerRepositoryPort, times(1)).findById(id);
        verify(customerRepositoryPort, times(1)).save(customerToUpdate);
    }

    @Test
    @DisplayName("Deve lançar CustomerNotFoundException quando ID não existe")
    void deveLancarExcecaoQuandoIdNaoEncontrado() {
        // --- ARRANGE ---
        Long idInexistente = 999L;

        Customer customerToUpdate = new Customer();
        customerToUpdate.setId(idInexistente);
        customerToUpdate.setName("Tentativa Falha");

        // Simula que NÃO encontrou o ID no banco
        when(customerRepositoryPort.findById(idInexistente)).thenReturn(Optional.empty());

        // --- ACT & ASSERT ---
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            updateCustomerService.execute(customerToUpdate);
        });

        // Verifica a mensagem da exceção
        assertEquals("Customer not found with ID: " + idInexistente, exception.getMessage());

        // --- VERIFICAÇÃO DE SEGURANÇA ---
        // Garante que o método save JAMAIS foi chamado se o ID não existe
        verify(customerRepositoryPort, never()).save(any());
    }
}