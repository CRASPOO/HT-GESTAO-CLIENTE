Grupo 96

Contribuições:
Camila Rabello Spoo Goshima - Discord: camilaspoo - 11 973091025
Rodrigo Rabello Spoo - Discord: srsinistro9459 - 11 981046096

Vídeo:
https://www.youtube.com/watch?v=YQabQ-ai_8I

Repositório:
https://github.com/2rspoo/gestao-clientes

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

##🧪 Testes e Qualidade
O projeto conta com testes unitários para validar regras de domínio, use cases e adaptadores.
Rodar Testes
Bash
mvn clean test
Relatório de Cobertura (JaCoCo)
Após a execução dos testes, o relatório de cobertura pode ser visualizado em:
target/site/jacoco/index.html
http://localhost:63342/gestao-clientes2/cardapio/target/site/jacoco/index.html?_ijt=sv3u32eq03tutu8i3128g8k9f2&_ij_reload=RELOAD_ON_SAVE
<img width="1313" height="228" alt="image" src="https://github.com/user-attachments/assets/b0d334ba-2587-42ba-970a-0e99ee6bf6f1" />


## 🥒 BDD (Behavior Driven Development)

Além dos testes unitários, a aplicação utiliza **Cucumber** para testes de comportamento, garantindo que as funcionalidades atendam aos requisitos de negócio descritos em linguagem natural (Gherkin).
📂 Estrutura dos Testes
* **Features (.feature):** Localizados em `src/test/resources/features`. Descrevem os cenários de uso (ex: Criar Pedido, Atualizar Status).
* **Step Definitions:** Localizados em `src/test/java/.../bdd`. Conectam os passos do Gherkin com o código Java.

▶️ Como Rodar os Testes BDD
Os testes BDD são executados juntamente com a suíte de testes principal ou através de um perfil específico (dependendo da sua configuração).
Bash
## Executa todos os testes (Unitários + BDD)
mvn clean test
Relatório do Cucumber: Após a execução, um relatório detalhado pode ser encontrado em: target/cucumber-reports/cucumber.html 
http://localhost:63342/gestao-clientes3/cliente/target/cucumber-reports/cucumber.html?_ijt=tnfatucmubv653vnu13aa0ogkg&_ij_reload=RELOAD_ON_SAVE

<img width="1077" height="223" alt="image" src="https://github.com/user-attachments/assets/9fd50287-652e-4dab-9891-2d74d8f55725" />


Análise de Código (SonarQube)
Para enviar as métricas para o Sonar:
Bash
mvn clean verify sonar:sonar -Dsonar.token=SEU_TOKEN
<img width="1112" height="190" alt="image" src="https://github.com/user-attachments/assets/4ce27368-0f93-4226-8890-9b8c414498a1" />


##🔌 API Endpoints (Resumo)
Método	Endpoint	Descrição
POST	/clientes	Cadastra um novo cliente (Nome, CPF, Email)
GET	/clientes/{cpf}	Identifica um cliente pelo CPF
GET	/clientes	Lista todos os clientes (Uso administrativo)



## Acesso ao Frontend da Aplicação:
Abra o arquivo index.html, webhook ou stress.html diretamente no seu navegador. As interfaces carregarão os dados da API.

## Acesso a Documentação da API (Swagger UI):
A documentação completa (Swagger) pode ser acessada em: http://localhost:8080/swagger-ui.html
A documentação interativa completa da API está disponível em:
http://localhost:30001/swagger-ui.html




