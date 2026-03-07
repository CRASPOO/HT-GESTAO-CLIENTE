package com.example.order.infrastructure.adapters.in.web.Controllers;

import com.example.order.application.ports.in.Customer.CreateCustomerUseCase;
import com.example.order.application.ports.in.Customer.GetAllCustomersUseCase;
import com.example.order.application.ports.in.Customer.GetCustomerByCpfUseCase;
import com.example.order.application.ports.in.Customer.UpdateCustomerUseCase;
import com.example.order.application.ports.in.Customer.LoginCustomerUseCase;
import com.example.order.domain.entities.Customer;
import com.example.order.infrastructure.adapters.in.web.Dtos.CustomerRequestDTO;
import com.example.order.infrastructure.adapters.in.web.Dtos.CustomerResponseDTO;
import com.example.order.infrastructure.adapters.in.web.Dtos.LoginRequestDTO;
import com.example.order.shared.exceptions.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final GetCustomerByCpfUseCase getCustomerByCpfUseCase;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final LoginCustomerUseCase loginCustomerUseCase;

    public CustomerController(
            GetAllCustomersUseCase getAllCustomersUseCase,
            GetCustomerByCpfUseCase getCustomerByCpfUseCase,
            CreateCustomerUseCase createCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            LoginCustomerUseCase loginCustomerUseCase) {
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getCustomerByCpfUseCase = getCustomerByCpfUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.loginCustomerUseCase = loginCustomerUseCase;
    }

    @Operation(description = "Retorna a lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de clientes"),
            @ApiResponse(responseCode = "400", description = "Não existe clientes")
    })
    @GetMapping
    public List<CustomerResponseDTO> getAll() {
        List<Customer> customers = getAllCustomersUseCase.execute();
        return customers.stream()
                .map(CustomerResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Operation(description = "Consulta Cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna cliente"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerResponseDTO> getByCpf(@PathVariable String cpf) {
        Customer customer = getCustomerByCpfUseCase.execute(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with CPF: " + cpf));
        return ResponseEntity.ok(CustomerResponseDTO.fromDomain(customer));
    }

    @Operation(description = "Insere um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna novo cliente"),
            @ApiResponse(responseCode = "409", description = "Cliente já existe")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO saveCustomer(@Valid @RequestBody CustomerRequestDTO data) {
        try {
            Customer newCustomer = new Customer();
            newCustomer.setName(data.name());
            newCustomer.setEmail(data.email());
            newCustomer.setCpf(data.cpf());
            newCustomer.setSenha(data.senha());

            Customer createdCustomer = createCustomerUseCase.execute(newCustomer);
            return CustomerResponseDTO.fromDomain(createdCustomer);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Operation(description = "Altera dados do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do cliente alterado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerRequestDTO data) {
        if (data.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer ID is mandatory for update.");
        }
        try {
            Customer customerToUpdate = new Customer();
            customerToUpdate.setId(data.id());
            customerToUpdate.setName(data.name());
            customerToUpdate.setEmail(data.email());
            customerToUpdate.setCpf(data.cpf());

            Customer updatedCustomer = updateCustomerUseCase.execute(customerToUpdate);
            return ResponseEntity.ok(CustomerResponseDTO.fromDomain(updatedCustomer));
        } catch (CustomerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(description = "Realiza o login do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<CustomerResponseDTO> login(@Valid @RequestBody LoginRequestDTO data) {
        try {
            Customer customer = loginCustomerUseCase.execute(data.cpf(), data.senha());
            return ResponseEntity.ok(CustomerResponseDTO.fromDomain(customer));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}