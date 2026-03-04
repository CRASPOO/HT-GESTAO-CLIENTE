package com.example.order.infrastructure.adapters.out.persistence.JpaEntity;

import com.example.order.domain.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Sobe um banco em memória (H2) e configura o Hibernate para teste
class CustomerJpaEntityTest {

    @Autowired
    private TestEntityManager entityManager; // Ferramenta do Spring para manipular o banco nos testes

    // --- Testes de Persistência (Banco de Dados) ---

    @Test
    @DisplayName("Deve persistir a entidade e gerar ID automaticamente")
    void devePersistirCustomer() {
        // Arrange
        CustomerJpaEntity entity = new CustomerJpaEntity(null, "Teste DB", "db@teste.com", "12345678900");

        // Act
        // PersistAndFlush salva no banco e força o envio do SQL
        CustomerJpaEntity savedEntity = entityManager.persistAndFlush(entity);

        // Assert
        assertThat(savedEntity.getId()).isNotNull(); // Verifica se o @GeneratedValue funcionou
        assertThat(savedEntity.getName()).isEqualTo("Teste DB");
        assertThat(savedEntity.getEmail()).isEqualTo("db@teste.com");
        assertThat(savedEntity.getCpf()).isEqualTo("12345678900");
    }

    // --- Testes de Mapeamento (Lógica de Conversão) ---

    @Test
    @DisplayName("Deve mapear de Domain para Entity corretamente (fromDomain)")
    void deveMapearFromDomain() {
        // Arrange
        Customer domainCustomer = new Customer();
        domainCustomer.setId(1L);
        domainCustomer.setName("Maria Domain");
        domainCustomer.setEmail("maria@domain.com");
        domainCustomer.setCpf("99988877700");

        // Act
        CustomerJpaEntity entity = CustomerJpaEntity.fromDomain(domainCustomer);

        // Assert
        assertThat(entity.getId()).isEqualTo(domainCustomer.getId());
        assertThat(entity.getName()).isEqualTo(domainCustomer.getName());
        assertThat(entity.getEmail()).isEqualTo(domainCustomer.getEmail());
        assertThat(entity.getCpf()).isEqualTo(domainCustomer.getCpf());
    }

    @Test
    @DisplayName("Deve mapear de Entity para Domain corretamente (toDomain)")
    void deveMapearToDomain() {
        // Arrange
        CustomerJpaEntity entity = new CustomerJpaEntity(5L, "João Entity", "joao@entity.com", "11122233344");

        // Act
        Customer domainCustomer = entity.toDomain();

        // Assert
        assertThat(domainCustomer).isNotNull();
        assertThat(domainCustomer.getId()).isEqualTo(entity.getId());
        assertThat(domainCustomer.getName()).isEqualTo(entity.getName());
        assertThat(domainCustomer.getEmail()).isEqualTo(entity.getEmail());
        assertThat(domainCustomer.getCpf()).isEqualTo(entity.getCpf());
    }

    @Test
    @DisplayName("Deve testar getters e setters básicos")
    void deveTestarGettersESetters() {
        CustomerJpaEntity entity = new CustomerJpaEntity();

        entity.setId(10L);
        entity.setName("Teste");
        entity.setEmail("teste@email.com");
        entity.setCpf("123");

        assertThat(entity.getId()).isEqualTo(10L);
        assertThat(entity.getName()).isEqualTo("Teste");
        assertThat(entity.getEmail()).isEqualTo("teste@email.com");
        assertThat(entity.getCpf()).isEqualTo("123");
    }
}