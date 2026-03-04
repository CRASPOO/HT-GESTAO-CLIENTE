package com.example.order.infrastructure.adapters.in.web.Dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // Configura o contexto de teste focado apenas em JSON (Jackson)
class CustomerRequestDTOTest {

    @Autowired
    private JacksonTester<CustomerRequestDTO> json;

    @Test
    @DisplayName("Deve serializar DTO para JSON corretamente")
    void deveSerializarParaJson() throws Exception {
        // Arrange
        CustomerRequestDTO dto = new CustomerRequestDTO(
                1L,
                "Maria",
                "maria@teste.com",
                "12345678900"
        );

        // Act
        JsonContent<CustomerRequestDTO> result = json.write(dto);

        // Assert
        // Verifica se o JSON gerado contém os campos e valores esperados
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Maria");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("maria@teste.com");
        assertThat(result).extractingJsonPathStringValue("$.cpf").isEqualTo("12345678900");
    }

    @Test
    @DisplayName("Deve deserializar JSON para DTO corretamente")
    void deveDeserializarDeJson() throws Exception {
        // Arrange
        String jsonContent = """
                {
                    "id": 2,
                    "name": "João",
                    "email": "joao@teste.com",
                    "cpf": "98765432100"
                }
                """;

        // Act
        CustomerRequestDTO dto = json.parseObject(jsonContent);

        // Assert
        assertThat(dto.id()).isEqualTo(2L);
        assertThat(dto.name()).isEqualTo("João");
        assertThat(dto.email()).isEqualTo("joao@teste.com");
        assertThat(dto.cpf()).isEqualTo("98765432100");
    }
}