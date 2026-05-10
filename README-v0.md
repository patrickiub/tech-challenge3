# Tech Challenge 2 — API REST de Gestão de Restaurantes

API REST desenvolvida com Spring Boot 3 e arquitetura Clean Architecture para o gerenciamento de restaurantes, usuários e cardápios. O sistema permite cadastrar tipos de usuário, usuários, restaurantes e itens de cardápio, mantendo as relações entre esses recursos.

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.10 |
| Spring Data JPA | — |
| MySQL | 8.0 |
| springdoc-openapi (Swagger) | 2.8.3 |
| Lombok | — |
| JaCoCo | 0.8.12 |
| Docker / Docker Compose | — |

---

## Arquitetura — Clean Architecture

O projeto segue os princípios da **Clean Architecture**, organizando o código em camadas com dependências que apontam sempre para o centro (domínio), sem acoplamento com frameworks externos nas regras de negócio.

```
src/main/java/.../
├── {modulo}/
│   ├── core/
│   │   ├── domain/       ← Entidades de domínio puro (sem anotações de framework)
│   │   ├── dto/          ← Records de entrada (Request) e saída (Response) com validações Jakarta
│   │   ├── exception/    ← Exceções de negócio (RuntimeException)
│   │   ├── gateway/      ← Interfaces que definem o contrato de persistência
│   │   └── usecase/      ← Casos de uso (@Service) com método executar()
│   └── infra/
│       ├── controller/   ← @RestController com mapeamentos HTTP e Swagger
│       └── gateway/
│           ├── db/
│           │   ├── entity/     ← @Entity JPA
│           │   └── repository/ ← JpaRepository
│           └── *GatewayImpl    ← Implementação da interface de gateway
```

### Responsabilidade de cada camada

| Camada | Responsabilidade |
|---|---|
| `core/domain` | Classes de domínio puro, sem dependência de frameworks. Representam os conceitos centrais do negócio. |
| `core/usecase` | Regras de negócio orquestradas em casos de uso independentes. Dependem apenas de interfaces de gateway. |
| `core/gateway` | Interfaces que abstraem o acesso a dados. O núcleo não conhece a implementação (JPA, banco etc.). |
| `core/dto` | Records Java com anotações de validação para entrada e saída de dados na API. |
| `core/exception` | Exceções semânticas de negócio lançadas pelos use cases. |
| `infra/controller` | Camada HTTP. Recebe requisições, converte DTOs em domínio, chama use cases e devolve respostas. |
| `infra/gateway` | Implementação JPA dos gateways. Converte domínio ↔ entidade e acessa o banco de dados. |

### Módulos do sistema

- **usuario** — Tipos de usuário e usuários com autenticação básica
- **restaurante** — Restaurantes associados a um dono (usuário)
- **cardapio** — Itens de cardápio associados a um restaurante, com categorias

---

## Endpoints

Base URL: `http://localhost:8080/api/v1`

### Tipos de Usuário

| Método | URL | Descrição | Status |
|---|---|---|---|
| `POST` | `/tipos-usuario` | Criar tipo de usuário | 201 / 400 |
| `GET` | `/tipos-usuario` | Listar todos os tipos | 200 |
| `GET` | `/tipos-usuario/{id}` | Buscar por ID | 200 / 404 |
| `PUT` | `/tipos-usuario/{id}` | Atualizar tipo | 200 / 400 / 404 |
| `DELETE` | `/tipos-usuario/{id}` | Remover tipo | 204 / 404 |

### Usuários

| Método | URL | Descrição | Status |
|---|---|---|---|
| `POST` | `/usuarios` | Criar usuário | 201 / 400 |
| `GET` | `/usuarios` | Listar todos os usuários | 200 |
| `GET` | `/usuarios/{id}` | Buscar por ID | 200 / 404 |
| `PUT` | `/usuarios/{id}` | Atualizar usuário | 200 / 400 / 404 |
| `DELETE` | `/usuarios/{id}` | Remover usuário | 204 / 404 |

### Restaurantes

| Método | URL | Descrição | Status |
|---|---|---|---|
| `POST` | `/restaurantes` | Criar restaurante | 201 / 400 |
| `GET` | `/restaurantes` | Listar todos os restaurantes | 200 |
| `GET` | `/restaurantes/{id}` | Buscar por ID | 200 / 404 |
| `PUT` | `/restaurantes/{id}` | Atualizar restaurante | 200 / 400 / 404 |
| `DELETE` | `/restaurantes/{id}` | Remover restaurante | 204 / 404 |

### Itens de Cardápio

| Método | URL | Descrição | Status |
|---|---|---|---|
| `POST` | `/itens-cardapio` | Criar item de cardápio | 201 / 400 |
| `GET` | `/itens-cardapio` | Listar todos os itens | 200 |
| `GET` | `/itens-cardapio/{id}` | Buscar por ID | 200 / 404 |
| `GET` | `/itens-cardapio/restaurante/{restauranteId}` | Listar itens de um restaurante | 200 |
| `PUT` | `/itens-cardapio/{id}` | Atualizar item | 200 / 400 / 404 |
| `DELETE` | `/itens-cardapio/{id}` | Remover item | 204 / 404 |

#### Categorias de item de cardápio

`ENTRADA` · `PRATO_PRINCIPAL` · `SOBREMESA` · `BEBIDA` · `LANCHE`

---

## Como executar

### Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/) e Docker Compose instalados **ou** JDK 17 + MySQL 8.0 rodando localmente.

---

### Opção 1 — Docker Compose (recomendado)

Sobe a aplicação e o banco MySQL em contêineres com um único comando:

```bash
docker-compose up --build
```

O que acontece:
1. Constrói a imagem da aplicação (multi-stage build com Maven + JRE 17 Alpine)
2. Sobe o MySQL 8.0 com health check
3. Aguarda o banco estar saudável e então inicia a aplicação

Para parar e remover os contêineres:

```bash
docker-compose down
```

Para remover também o volume de dados do MySQL:

```bash
docker-compose down -v
```

Variáveis de ambiente configuradas no `docker-compose.yml`:

| Variável | Valor padrão |
|---|---|
| `DB_URL` | `jdbc:mysql://mysql:3306/techchallenge2?...` |
| `DB_USERNAME` | `fiap` |
| `DB_PASSWORD` | `fiap123` |
| `DDL_AUTO` | `update` |
| `JPA_SHOW_SQL` | `false` |

---

### Opção 2 — Execução local (sem Docker)

**Pré-requisitos:** MySQL 8.0 rodando em `localhost:3306` com banco `techchallenge2`, usuário `fiap` e senha `fiap123` (ou configure as variáveis de ambiente abaixo).

```bash
./mvnw spring-boot:run
```

Para sobrescrever as configurações de banco:

```bash
./mvnw spring-boot:run \
  -Dspring-boot.run.jvmArguments="\
    -DDB_URL=jdbc:mysql://localhost:3306/techchallenge2?createDatabaseIfNotExist=true&useSSL=false \
    -DDB_USERNAME=seu_usuario \
    -DDB_PASSWORD=sua_senha"
```

A aplicação sobe na porta **8080** por padrão.

---

## Swagger / OpenAPI

Com a aplicação em execução, acesse a documentação interativa da API:

```
http://localhost:8080/swagger-ui.html
```

O JSON da especificação OpenAPI 3 está disponível em:

```
http://localhost:8080/v3/api-docs
```

---

## Testes

### Executar todos os testes (unitários + integração)

```bash
./mvnw test
```

- **Testes unitários** — use cases testados com JUnit 5 e Mockito
- **Testes de integração** — controllers testados com `@SpringBootTest` + `MockMvc` + H2 in-memory

### Relatório de cobertura (JaCoCo)

```bash
./mvnw test
```

O relatório é gerado automaticamente ao final dos testes em:

```
target/site/jacoco/index.html
```

Cobertura atual: **≥ 98%** de instruções.

---

## Estrutura do projeto

```
tech-challenge2/
├── src/
│   ├── main/
│   │   ├── java/.../
│   │   │   ├── TechChallenge2Application.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── usuario/
│   │   │   ├── restaurante/
│   │   │   └── cardapio/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql               ← Dados iniciais (INSERT IGNORE)
│   └── test/
│       ├── java/.../
│       │   ├── usuario/core/usecase/  ← Testes unitários
│       │   ├── restaurante/core/usecase/
│       │   ├── cardapio/core/usecase/
│       │   ├── usuario/infra/controller/   ← Testes de integração
│       │   ├── restaurante/infra/controller/
│       │   └── cardapio/infra/controller/
│       └── resources/
│           └── application.properties ← H2 in-memory para testes
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## Dados iniciais

Ao subir a aplicação com MySQL, o arquivo `data.sql` insere automaticamente dados de exemplo:

- **Tipos de usuário:** `Dono de Restaurante`, `Cliente`
- **Usuários:** João Silva (dono), Maria Souza (cliente)
- **Restaurante:** Restaurante do João (Brasileira)
- **Itens de cardápio:** Coxinha de Frango, Feijoada Completa, Pudim de Leite
