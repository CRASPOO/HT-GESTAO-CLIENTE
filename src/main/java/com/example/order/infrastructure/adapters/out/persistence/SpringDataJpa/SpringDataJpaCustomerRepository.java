// src/main/java/com/example/order/infrastructure/adapters/out/persistence/SpringDataJpaCustomerRepository.java
package com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa;

import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJpaCustomerRepository extends JpaRepository<CustomerJpaEntity, Long> {
    Optional<CustomerJpaEntity> findByCpf(String cpf);
}