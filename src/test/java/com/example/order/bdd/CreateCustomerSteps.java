package com.example.order.bdd;

import com.example.order.application.ports.in.Customer.CreateCustomerUseCase;
import com.example.order.domain.entities.Customer;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class CreateCustomerSteps {

    // 1. Substitua @MockBean por instanciar um mock normal
    // Isso é mais rápido e não depende da versão do Spring
    private CreateCustomerUseCase createCustomerUseCase = mock(CreateCustomerUseCase.class);

    private Customer input;
    private Customer created;
    private Exception exception;
    private Customer outputEsperado;

    @Dado("um cliente válido com nome {string} e email {string}")
    public void um_cliente_valido_com_nome_e_email(String nome, String email) {
        // --- PREPARAÇÃO DO INPUT ---
        input = new Customer();
        input.setName(nome);
        input.setEmail(email);
        input.setCpf("39475318852");

        // --- PREPARAÇÃO DO OUTPUT (O que o Mock vai retornar) ---
        outputEsperado = new Customer();
        outputEsperado.setId(1L);
        outputEsperado.setName(nome);
        outputEsperado.setEmail(email);
        outputEsperado.setCpf("39475318852");

        // 2. IMPORTANTE: Configure o comportamento do Mock aqui no @Dado (Pré-condição)
        // Você diz: "Quando o caso de uso for chamado com 'input', retorne 'outputEsperado'"
        when(createCustomerUseCase.execute(input)).thenReturn(outputEsperado);
    }

    @Quando("eu criar o cliente")
    public void eu_criar_o_cliente() {
        try {
            // 3. No @Quando, apenas EXECUTE a ação. Não configure mocks aqui.
            created = createCustomerUseCase.execute(input);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Entao("o cliente é criado com id")
    public void o_cliente_e_criado_com_id() {
        assertNull(exception, "Não deveria ter lançado erro: " + exception);
        assertNotNull(created, "O objeto criado não deveria ser nulo");

        // Valida se o ID existe e se é igual ao que o Mock retornou
        assertNotNull(created.getId());
        assertEquals(1L, created.getId());
    }
}