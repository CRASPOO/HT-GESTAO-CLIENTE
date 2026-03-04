# language: pt
Funcionalidade: Gestão de Clientes
  Como um administrador do sistema
  Eu quero cadastrar novos clientes
  Para que eles possam realizar pedidos

  Cenário: Criar cliente com sucesso
    Dado um cliente válido com nome "Maria2" e email "maria@example.com"
    Quando eu criar o cliente
    Então o cliente é criado com id
