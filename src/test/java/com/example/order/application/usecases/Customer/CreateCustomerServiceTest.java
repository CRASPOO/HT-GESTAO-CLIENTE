package com.example.order.application.usecases.Customer;

import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa os Mocks automaticamente
class CreateCustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @InjectMocks
    private CreateCustomerService createCustomerService;

    @Test
    @DisplayName("Deve criar cliente com sucesso quando CPF não existe")
    void deveCriarClienteComSucesso() {
        // --- ARRANGE (Preparação) ---
        String cpf = "12345678901";
        Customer customerInput = new Customer();
        customerInput.setCpf(cpf);
        customerInput.setName("Maria");
        customerInput.setEmail("maria@teste.com");

        // Simula que NÃO encontrou ninguém com esse CPF (Optional.empty)
        when(customerRepositoryPort.findByCpf(cpf)).thenReturn(Optional.empty());

        // Simula o salvamento retornando o próprio objeto (ou um com ID preenchido)
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(customerInput);

        // --- ACT (Ação) ---
        Customer resultado = createCustomerService.execute(customerInput);

        // --- ASSERT (Verificação) ---
        assertNotNull(resultado);
        assertEquals(cpf, resultado.getCpf());
        assertEquals("Maria", resultado.getName());

        // Verifica se o método save foi chamado exatamente 1 vez
        verify(customerRepositoryPort, times(1)).save(customerInput);
        // Verifica se o método findByCpf foi chamado
        verify(customerRepositoryPort, times(1)).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existe")
    void deveLancarExcecaoQuandoCpfJaExiste() {
        // --- ARRANGE (Preparação) ---
        String cpf = "99988877700";
        Customer customerInput = new Customer();
        customerInput.setCpf(cpf);

        Customer clienteExistente = new Customer();
        clienteExistente.setCpf(cpf);
        clienteExistente.setName("João Já Existe");

        // Simula que JÁ encontrou um cliente (Optional.of)
        when(customerRepositoryPort.findByCpf(cpf)).thenReturn(Optional.of(clienteExistente));

        // --- ACT & ASSERT (Ação e Verificação) ---
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCustomerService.execute(customerInput);
        });

        // Verifica a mensagem de erro
        assertEquals("Customer with this CPF already exists.", exception.getMessage());

        // --- VERIFICAÇÕES IMPORTANTES ---
        // Garante que o método save NUNCA foi chamado (proteção da regra de negócio)
        verify(customerRepositoryPort, never()).save(any());
    }
}