package com.example.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // <--- Importe isso

@SpringBootTest
@ActiveProfiles("test") // <--- Adicione essa linha para forçar o uso do H2
class OrderApplicationTest {

    @Test
    @DisplayName("Deve carregar o contexto da aplicação sem erros")
    void contextLoads() {
        // Teste de sanidade (Sanity Check)
    }
}