Grupo 82

Contribuições:
Camila Rabello Spoo Goshima - Discord: camilaspoo - 11 973091025
Rodrigo Rabello Spoo - Discord: srsinistro9459 - 11 981046096


## 👤 Gestão de Clientes
Este projeto é um microsserviço responsável pela gestão e identificação de clientes da lanchonete. Ele permite o cadastro de novos clientes e a identificação por CPF, garantindo a centralização dos dados de usuários para os demais serviços do ecossistema.
O projeto segue estritamente os princípios da **Arquitetura Hexagonal (Ports and Adapters)** para garantir manutenibilidade e desacoplamento.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)
![Coverage](https://img.shields.io/badge/Coverage-Jacoco-success)
![Build](https://img.shields.io/badge/Build-Maven-blue)

## 🏛️ Arquitetura
A aplicação foi desenhada para isolar o domínio das implementações externas:
* **Domain:** Entidades centrais (`Cliente`, `CPF`, etc) e regras de negócio (validação de CPF, unicidade).
* **Application (Use Cases):** Casos de uso como `CreateClient`, `FindClientByCpf`.
* **Ports (In/Out):** Contratos que definem como o mundo externo interage com a aplicação e como a aplicação persiste dados.
* **Infrastructure (Adapters):** Implementações de Banco de Dados (Repository) e Controladores REST.

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.4.1
* **Banco de Dados:** PostgreSQL/MySQL 
* **Documentação:** SpringDoc OpenAPI (Swagger)
* **Qualidade:** JaCoCo (Cobertura de testes), SonarQube
* **Containerização:** Docker

## 🚀 Como Rodar o Projeto
## Pré-requisitos
* Java 21 SDK
* Maven
* Docker (opcional)

## Configuração de Ambiente
Configure as variáveis necessárias no arquivo `application.properties` ou via variáveis de ambiente:
spring.datasource.url=jdbc:postgresql://localhost:5432/clientes
spring.datasource.username=user
spring.datasource.password=pass
Executando a Aplicação
Bash
mvn spring-boot:run

##🔌 API Endpoints (Resumo)
Método	Endpoint	Descrição
POST	/clientes	Cadastra um novo cliente (Nome, CPF, Email)
GET	/clientes/{cpf}	Identifica um cliente pelo CPF
GET	/clientes	Lista todos os clientes (Uso administrativo)









