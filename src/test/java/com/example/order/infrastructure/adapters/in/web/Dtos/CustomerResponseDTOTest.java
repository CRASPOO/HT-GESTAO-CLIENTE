package com.example.order.infrastructure.adapters.in.web.Dtos;

import com.example.order.domain.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // Configura o ambiente para testar serialização JSON
class CustomerResponseDTOTest {

    @Autowired
    private JacksonTester<CustomerResponseDTO> json;

    // --- Teste de Unidade (Lógica de Mapeamento) ---

    @Test
    @DisplayName("Deve converter Entidade Customer para DTO corretamente")
    void deveConverterDeDominio() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(10L);
        customer.setName("Teste de Mapeamento");
        customer.setEmail("map@teste.com");
        customer.setCpf("12345678900");

        // Act
        CustomerResponseDTO dto = CustomerResponseDTO.fromDomain(customer);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(customer.getId());
        assertThat(dto.getName()).isEqualTo(customer.getName());
        assertThat(dto.getEmail()).isEqualTo(customer.getEmail());
        assertThat(dto.getCpf()).isEqualTo(customer.getCpf());
    }

    // --- Teste de Serialização (JSON) ---

    @Test
    @DisplayName("Deve serializar DTO para JSON corretamente")
    void deveSerializarParaJson() throws Exception {
        // Arrange
        CustomerResponseDTO dto = new CustomerResponseDTO(
                1L,
                "Maria",
                "maria@teste.com",
                "98765432100"
        );

        // Act
        JsonContent<CustomerResponseDTO> result = json.write(dto);

        // Assert
        // Verifica se os campos existem no JSON final
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Maria");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("maria@teste.com");
        assertThat(result).extractingJsonPathStringValue("$.cpf").isEqualTo("98765432100");
    }

    @Test
    @DisplayName("Deve deserializar JSON para DTO corretamente")
    void deveDeserializarDeJson() throws Exception {
        // Arrange
        String jsonContent = """
                {
                    "id": 5,
                    "name": "João",
                    "email": "joao@teste.com",
                    "cpf": "11122233344"
                }
                """;

        // Act
        CustomerResponseDTO dto = json.parseObject(jsonContent);

        // Assert
        // Verifica se os setters funcionaram
        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getName()).isEqualTo("João");
        assertThat(dto.getEmail()).isEqualTo("joao@teste.com");
        assertThat(dto.getCpf()).isEqualTo("11122233344");
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void deveTestarGettersESetters() {
        // Como a classe não usa Lombok, é bom garantir que não houve erro de digitação
        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setId(1L);
        dto.setName("Teste");
        dto.setEmail("teste@email.com");
        dto.setCpf("123");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Teste");
        assertThat(dto.getEmail()).isEqualTo("teste@email.com");
        assertThat(dto.getCpf()).isEqualTo("123");
    }
}