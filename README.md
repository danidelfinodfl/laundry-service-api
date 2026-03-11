# Laundry Orders API

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de pedidos de lavanderia, utilizando **AWS DynamoDB** como banco de dados e integração com **ViaCEP** para obtenção automática de endereços a partir do CEP.

O objetivo do projeto é demonstrar boas práticas de desenvolvimento backend, organização em camadas, integração com serviços externos e uso de banco NoSQL em ambiente cloud.

---

# 📌 Problema que a aplicação resolve

Pequenos negócios como lavanderias muitas vezes controlam pedidos utilizando:

* cadernos
* planilhas
* anotações manuais

Isso pode gerar problemas como:

* dificuldade para localizar pedidos
* falta de histórico de clientes
* inconsistência de endereços
* dificuldade de acompanhar status de serviços

A **Laundry Orders API** foi criada para digitalizar esse processo e organizar o fluxo de pedidos.

---

# 🎯 Objetivo do projeto

Demonstrar a construção de uma API backend com:

* arquitetura organizada
* separação de responsabilidades
* integração com API externa
* persistência em banco NoSQL
* princípios de boas práticas de engenharia de software

---

# 🚀 Tecnologias utilizadas

### Linguagem

* Java 21

### Framework

* Spring Boot

### Banco de dados

* AWS DynamoDB

### Build

* Maven

### Integrações externas

* ViaCEP API

### Ferramentas de desenvolvimento

* IntelliJ IDEA
* Insomnia / Postman
* Git
* GitHub

---

# 🏗️ Arquitetura da aplicação

A aplicação segue uma organização inspirada em **Hexagonal Architecture**, separando responsabilidades em diferentes camadas.

Principais camadas:

* **Controller** → recebe requisições HTTP
* **UseCases** → regras de negócio
* **Repository** → comunicação com banco de dados
* **Domain** → entidades da aplicação
* **Adapters** → integração com serviços externos

---

# 📂 Estrutura de pastas

```text
src
 └── main
     └── java
         └── com.laundry
             ├── controllers
             │     └── OrderController.java
             │
             ├── application
             │     └── usecases
             │           ├── CreateOrderUsecase.java
             │           ├── GetOrderUsecase.java
             │           ├── GetOrdersByClientUsecase.java
             │           └── UpdateOrderStatusUsecase.java
             │
             ├── domain
             │     └── Order.java
             │
             ├── repository
             │     ├── OrderRepository.java
             │     └── DynamoDbOrderRepository.java
             │
             ├── adapters
             │     └── ViaCepClient.java
             │
             ├── config
             │     ├── DynamoDbConfig.java
             │     └── ExceptionHandler.java
             │
             └── LaundryApiApplication.java
```

Essa separação facilita:

* manutenção
* evolução da aplicação
* testes
* substituição de tecnologias no futuro

---

# 🔄 Fluxo da aplicação

Fluxo simplificado de criação de pedido:

```
Cliente
   │
   ▼
Controller (Spring Boot)
   │
   ▼
UseCase (regra de negócio)
   │
   ▼
Consulta API ViaCEP
   │
   ▼
Repository
   │
   ▼
DynamoDB
```

### Passo a passo

1. Cliente envia requisição HTTP
2. Controller recebe o request
3. UseCase executa regra de negócio
4. API consulta endereço via CEP
5. Pedido é salvo no DynamoDB
6. Resposta retorna ao cliente

---

# 🗄️ Banco de dados — DynamoDB

Tabela utilizada:

```
orders
```

Chave primária:

```
id
```

Cada registro representa um pedido.

### Exemplo de item

```json
{
  "id": "123",
  "nomeCliente": "João",
  "cpfCliente": "12345678900",
  "descricaoPedido": "3 camisas",
  "cepCliente": "01001000",
  "rua": "Praça da Sé",
  "cidade": "São Paulo",
  "estado": "SP",
  "status": "CRIADO"
}
```

---

# ⚡ Índice secundário

Para permitir busca por cliente foi criado um **Global Secondary Index (GSI)**.

Index:

```
cpf-index
```

Partition Key:

```
cpfCliente
```

Isso permite buscar rapidamente todos os pedidos de um cliente.

---

# 📡 Endpoints da API

## Criar pedido

```
POST /pedidos
```

### Exemplo de request

```json
{
 "nomeCliente": "João",
 "cpfCliente": "12345678900",
 "descricaoPedido": "2 edredons",
 "cepCliente": "01001000",
 "numero": "100",
 "complemento": "apto 101"
}
```

---

## Buscar pedido por ID

```
GET /pedidos/{id}
```

---

## Buscar pedidos por cliente

```
GET /pedidos/cliente/{cpf}
```

---

## Atualizar status do pedido

```
PATCH /pedidos/{id}/status
```

---

## Cancelar pedido

```
DELETE /pedidos/{id}
```

---

# 🧠 Princípios de engenharia utilizados

Alguns princípios do **SOLID** foram aplicados:

### Single Responsibility

Cada classe possui uma única responsabilidade:

* Controller → HTTP
* UseCase → regras de negócio
* Repository → persistência

---

### Dependency Inversion

UseCases dependem de abstrações:

```
OrderRepository
```

e não da implementação concreta.

Isso permite trocar o banco futuramente sem alterar a lógica da aplicação.

---

# 🔍 Integração com API externa

A aplicação utiliza a API pública **ViaCEP** para obter automaticamente os dados de endereço a partir do CEP informado.

Exemplo de chamada:

```
https://viacep.com.br/ws/{cep}/json
```

Isso reduz erros de digitação e melhora a experiência do usuário.

---

# ▶️ Como executar o projeto

### 1️⃣ Clonar repositório

```
git clone https://github.com/seuusuario/laundry-api
```

---

### 2️⃣ Entrar na pasta do projeto

```
cd laundry-api
```

---

### 3️⃣ Instalar dependências

```
mvn clean install
```

---

### 4️⃣ Executar aplicação

```
mvn spring-boot:run
```

A aplicação iniciará em:

```
http://localhost:8080
```

---

# 🧪 Testando a API

Ferramentas recomendadas:

* Insomnia
* Postman
* Curl

---

# 🔮 Melhorias futuras

Algumas evoluções possíveis:

* DTO e Mapper para desacoplamento de camadas
* autenticação com JWT
* documentação com Swagger/OpenAPI
* logs estruturados e observabilidade
* testes unitários e testes de integração
* containerização com Docker

---

# 👨‍💻 Autor

Projeto desenvolvido para estudo de arquitetura backend utilizando Java, Spring Boot e serviços cloud.
