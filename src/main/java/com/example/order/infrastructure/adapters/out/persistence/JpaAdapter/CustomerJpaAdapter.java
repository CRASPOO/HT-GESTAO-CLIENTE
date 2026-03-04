// src/main/java/com/example/order/infrastructure/adapters/out/persistence/CustomerJpaAdapter.java
package com.example.order.infrastructure.adapters.out.persistence.JpaAdapter;

import com.example.order.application.ports.out.CustomerRepositoryPort;
import com.example.order.domain.entities.Customer;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.CustomerJpaEntity;
import com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa.SpringDataJpaCustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerJpaAdapter implements CustomerRepositoryPort {

    private final SpringDataJpaCustomerRepository jpaRepository;

    public CustomerJpaAdapter(SpringDataJpaCustomerRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream()
                .map(CustomerJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        return jpaRepository.findByCpf(cpf)
                .map(CustomerJpaEntity::toDomain);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id)
                .map(CustomerJpaEntity::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity jpaEntity = CustomerJpaEntity.fromDomain(customer);
        CustomerJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return savedEntity.toDomain();
    }
}