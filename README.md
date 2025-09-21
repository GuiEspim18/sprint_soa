# InvestYou API

## Integrantes

- **RM99499 – Guilherme Monteiro Espim**
- **RM99279 – João Paulo Fonseca Zamperlini**
- **RM98297 – Lucas Pisaneschi Speranzini - 3ESPV (Noturno)**
- **RM97937 – Pedro Henrique Fernandes Lô de Barros**
- **RM97824 – Vinicius Oliveira de Barros**

Este projeto é uma API RESTful desenvolvida com Spring Boot, projetada para gerenciar informações relacionadas a usuários, suas experiências financeiras, metas, portfólios de investimento e investimentos individuais. A aplicação visa fornecer uma base robusta para um sistema de gestão de investimentos pessoais, permitindo o registro e acompanhamento de diversos aspectos da jornada financeira do usuário.

## Funcionalidades Principais

- **Gestão de Usuários**: Criação, leitura, atualização e exclusão de usuários.
- **Gestão de Experiências**: Registro de experiências financeiras dos usuários, incluindo período, reação, valor e receita.
- **Gestão de Metas**: Definição e acompanhamento de metas financeiras associadas a experiências específicas.
- **Gestão de Portfólios**: Criação e manutenção de portfólios de investimento para cada usuário.
- **Gestão de Investimentos**: Adição, atualização e remoção de investimentos dentro de um portfólio, com detalhes como tipo, nome, código, descrição e status, além de atributos personalizados.




## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação.
- **Spring Boot 3.5.5**: Framework para construção de aplicações Java.
- **Spring Web**: Para construção de APIs RESTful.
- **Spring Data JPA**: Para persistência de dados com Hibernate.
- **Lombok**: Para reduzir o código boilerplate.
- **MySQL**: Banco de dados relacional.
- **Flyway**: Para controle de versão do banco de dados.
- **Maven**: Ferramenta de automação de build.

## Configuração e Execução

Para configurar e executar o projeto localmente, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados em sua máquina:

- Java Development Kit (JDK) 17 ou superior
- Maven 3.x
- MySQL Server

### Configuração do Banco de Dados

1. Crie um banco de dados MySQL com o nome `invest_you`.
2. Atualize as credenciais do banco de dados no arquivo `src/main/resources/application.properties` (se necessário):

   ```properties
   spring.datasource.url=jdbc:mysql://localhost/invest_you
   spring.datasource.username=root
   spring.datasource.password=root
   ```

### Execução da Aplicação

1. Clone o repositório (se aplicável).
2. Navegue até o diretório raiz do projeto.
3. Compile o projeto usando Maven:

   ```bash
   mvn clean install
   ```

4. Execute a aplicação Spring Boot:

   ```bash
   mvn spring-boot:run
   ```

   A aplicação estará disponível em `http://localhost:8080`.




## Exemplos de Requisições e Respostas

### Usuários (User)

#### Criar Usuário

**POST** `/api/users`

**Requisição:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Listar Usuários

**GET** `/api/users`

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
]
```

#### Obter Usuário por ID

**GET** `/api/users/{id}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Atualizar Usuário

**PUT** `/api/users/{id}`

**Requisição:**
```json
{
  "name": "John Doe Updated",
  "email": "john.doe.updated@example.com"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "email": "john.doe.updated@example.com"
}
```

#### Deletar Usuário

**DELETE** `/api/users/{id}`

**Resposta (204 No Content)**




### Experiências (Experience)

#### Criar Experiência

**POST** `/api/experiences`

**Requisição:**
```json
{
  "userId": 1,
  "period": "2023-01-01 to 2023-12-31",
  "reaction": "Positive",
  "value": 1000.00,
  "revenue": 150.00
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "userId": 1,
  "period": "2023-01-01 to 2023-12-31",
  "reaction": "Positive",
  "value": 1000.00,
  "revenue": 150.00
}
```

#### Listar Experiências

**GET** `/api/experiences`

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "userId": 1,
    "period": "2023-01-01 to 2023-12-31",
    "reaction": "Positive",
    "value": 1000.00,
    "revenue": 150.00
  }
]
```

#### Obter Experiência por ID

**GET** `/api/experiences/{id}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "period": "2023-01-01 to 2023-12-31",
  "reaction": "Positive",
  "value": 1000.00,
  "revenue": 150.00
}
```

#### Atualizar Experiência

**PUT** `/api/experiences/{id}`

**Requisição:**
```json
{
  "period": "2023-01-01 to 2023-12-31",
  "reaction": "Very Positive",
  "value": 1200.00,
  "revenue": 200.00
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "period": "2023-01-01 to 2023-12-31",
  "reaction": "Very Positive",
  "value": 1200.00,
  "revenue": 200.00
}
```

#### Deletar Experiência

**DELETE** `/api/experiences/{id}`

**Resposta (204 No Content)**




### Metas (Goal)

#### Criar Meta

**POST** `/api/goals`

**Requisição:**
```json
{
  "experienceId": 1,
  "goal": "Comprar um carro novo"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "experienceId": 1,
  "goal": "Comprar um carro novo"
}
```

#### Listar Metas

**GET** `/api/goals`

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "experienceId": 1,
    "goal": "Comprar um carro novo"
  }
]
```

#### Obter Meta por ID

**GET** `/api/goals/{id}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "experienceId": 1,
  "goal": "Comprar um carro novo"
}
```

#### Atualizar Meta

**PUT** `/api/goals/{id}`

**Requisição:**
```json
{
  "goal": "Comprar um carro novo em 2025"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "experienceId": 1,
  "goal": "Comprar um carro novo em 2025"
}
```

#### Deletar Meta

**DELETE** `/api/goals/{id}`

**Resposta (204 No Content)**




### Portfólios (Portfolio)

#### Criar Portfólio

**POST** `/api/portfolios`

**Requisição:**
```json
{
  "userId": 1
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "userId": 1
}
```

#### Obter Portfólio por ID

**GET** `/api/portfolios/{id}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "userId": 1
}
```




### Investimentos (Investment)

#### Criar Investimento

**POST** `/api/investments`

**Requisição:**
```json
{
  "portfolioId": 1,
  "type": "Ação",
  "name": "Empresa X",
  "code": "EMP3",
  "description": "Ações da Empresa X no setor de tecnologia.",
  "status": "Ativo",
  "attributes": [
    {
      "type": "Setor",
      "value": "Tecnologia"
    },
    {
      "type": "Risco",
      "value": "Alto"
    }
  ]
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "portfolioId": 1,
  "type": "Ação",
  "name": "Empresa X",
  "code": "EMP3",
  "description": "Ações da Empresa X no setor de tecnologia.",
  "status": "Ativo"
}
```

#### Listar Investimentos

**GET** `/api/investments`

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "portfolioId": 1,
    "type": "Ação",
    "name": "Empresa X",
    "code": "EMP3",
    "description": "Ações da Empresa X no setor de tecnologia.",
    "status": "Ativo"
  }
]
```

#### Obter Investimento por ID

**GET** `/api/investments/{id}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "portfolioId": 1,
  "type": "Ação",
  "name": "Empresa X",
  "code": "EMP3",
  "description": "Ações da Empresa X no setor de tecnologia.",
  "status": "Ativo"
}
```

#### Atualizar Investimento

**PUT** `/api/investments/{id}`

**Requisição:**
```json
{
  "type": "Ação",
  "name": "Empresa X Atualizada",
  "code": "EMP3",
  "description": "Ações da Empresa X no setor de tecnologia, atualizadas.",
  "status": "Inativo"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "portfolioId": 1,
  "type": "Ação",
  "name": "Empresa X Atualizada",
  "code": "EMP3",
  "description": "Ações da Empresa X no setor de tecnologia, atualizadas.",
  "status": "Inativo"
}
```

#### Deletar Investimento

**DELETE** `/api/investments/{id}`

**Resposta (204 No Content)**


