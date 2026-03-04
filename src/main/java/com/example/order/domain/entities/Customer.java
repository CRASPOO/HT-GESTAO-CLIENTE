// src/main/java/com/example/order/domain/entities/Customer.java
//package com.example.order.domain.entities;
//
//import java.util.Objects;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@AllArgsConstructor // Gera o construtor com tudo
//@NoArgsConstructor
//
//public class Customer {
//
//    private Long id;
//    private String name;
//    private String email;
//    private Long cpf;
//
//    public Customer(String name, String email, Long cpf) {
//        this.name = name;
//        this.email = email;
//        this.cpf = cpf;
//    }
//
//    public Customer(Long id, String name, String email, Long cpf) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.cpf = cpf;
//    }
//
//    public Long getId() { return id; }
//    public String getName() { return name; }
//    public String getEmail() { return email; }
//    public Long getCpf() { return cpf; }
//
//    public void setId(Long id) { this.id = id; }
//    public void setName(String name) { this.name = name; }
//    public void setEmail(String email) { this.email = email; }
//    public void setCpf(Long cpf) { this.cpf = cpf; }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Customer customer = (Customer) o;
//        return Objects.equals(id, customer.id) && Objects.equals(cpf, customer.cpf);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, cpf);
//    }
//}
package com.example.order.domain.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data                 // Gera Getters, Setters, Equals, HashCode e ToString
@AllArgsConstructor   // Gera o construtor com TODOS os campos (substitui o manual)
@NoArgsConstructor    // Gera o construtor VAZIO (essencial para o teste)
public class Customer {

    private Long id;

    // Adicionado para passar no teste "deveDetectarNomeEmBranco"
    @NotBlank(message = "O nome não pode estar em branco")
    private String name;

    // Adicionado para passar no teste "deveDetectarEmailInvalido"
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @Range(min = 10000000000L, max = 99999999999L, message = "CPF deve ter 11 dígitos")
    private String cpf;

}