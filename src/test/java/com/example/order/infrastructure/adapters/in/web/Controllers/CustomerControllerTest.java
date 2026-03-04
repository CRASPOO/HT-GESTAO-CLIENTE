package com.example.order.infrastructure.adapters.in.web.Controllers;

import com.example.order.application.ports.in.Customer.CreateCustomerUseCase;
import com.example.order.application.ports.in.Customer.GetAllCustomersUseCase;
import com.example.order.application.ports.in.Customer.GetCustomerByCpfUseCase;
import com.example.order.application.ports.in.Customer.UpdateCustomerUseCase;
import com.example.order.domain.entities.Customer;
import com.example.order.infrastructure.adapters.in.web.Dtos.CustomerRequestDTO;
import com.example.order.shared.exceptions.CustomerNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // <--- NOVA IMPORTAÇÃO
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // --- AQUI ESTÁ A MUDANÇA: Use @MockitoBean em vez de @MockBean ---

    @MockitoBean
    private GetAllCustomersUseCase getAllCustomersUseCase;

    @MockitoBean
    private GetCustomerByCpfUseCase getCustomerByCpfUseCase;

    @MockitoBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockitoBean
    private UpdateCustomerUseCase updateCustomerUseCase;

    // --- O RESTANTE DO CÓDIGO PERMANECE IGUAL ---

    @Test
    @DisplayName("GET /customer - Deve retornar lista de clientes (200 OK)")
    void deveRetornarListaDeClientes() throws Exception {
        Customer c1 = new Customer(1L, "Maria", "maria@test.com", "11122233344");
        Customer c2 = new Customer(2L, "Joao", "joao@test.com", "55566677788");
        List<Customer> customers = Arrays.asList(c1, c2);

        when(getAllCustomersUseCase.execute()).thenReturn(customers);

        mockMvc.perform(get("/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Maria"));
    }

    @Test
    @DisplayName("GET /customer/cpf/{cpf} - Deve retornar cliente quando existir (200 OK)")
    void deveRetornarClientePorCpf() throws Exception {
        String cpf = "12345678900";
        Customer customer = new Customer(1L, "Carlos", "carlos@test.com", cpf);

        when(getCustomerByCpfUseCase.execute(cpf)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customer/cpf/{cpf}", cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.name").value("Carlos"));
    }

    @Test
    @DisplayName("GET /customer/cpf/{cpf} - Deve retornar 404 quando cliente não existir")
    void deveRetornar404QuandoCpfNaoEncontrado() throws Exception {
        String cpf = "99999999999";
        when(getCustomerByCpfUseCase.execute(cpf)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customer/cpf/{cpf}", cpf))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /customer - Deve criar cliente com sucesso (201 Created)")
    void deveCriarCliente() throws Exception {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO(null, "Ana", "ana@test.com", "12345678900");
        Customer createdCustomer = new Customer(10L, "Ana", "ana@test.com", "12345678900");

        when(createCustomerUseCase.execute(any(Customer.class))).thenReturn(createdCustomer);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Ana"));
    }

    @Test
    @DisplayName("POST /customer - Deve retornar 409 Conflict se cliente já existe")
    void deveRetornarConflitoSeClienteJaExiste() throws Exception {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO(null, "Duplicado", "dup@test.com", "11111111111");

        when(createCustomerUseCase.execute(any(Customer.class)))
                .thenThrow(new IllegalArgumentException("Customer with this CPF already exists."));

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PUT /customer - Deve atualizar cliente com sucesso (200 OK)")
    void deveAtualizarCliente() throws Exception {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO(1L, "Nome Novo", "novo@test.com", "12345678900");
        Customer updatedCustomer = new Customer(1L, "Nome Novo", "novo@test.com", "12345678900");

        when(updateCustomerUseCase.execute(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nome Novo"));
    }

    @Test
    @DisplayName("PUT /customer - Deve retornar 400 Bad Request se ID for nulo")
    void deveRetornarBadRequestSeIdNulo() throws Exception {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO(null, "Sem ID", "email@test.com", "123");

        mockMvc.perform(put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /customer - Deve retornar 404 Not Found se cliente não existir para atualização")
    void deveRetornarNotFoundAoAtualizarInexistente() throws Exception {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO(99L, "Inexistente", "email@test.com", "123");

        when(updateCustomerUseCase.execute(any(Customer.class)))
                .thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }
}