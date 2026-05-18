# Tech Challenge 3 — Sistema de Pedidos Online com Arquitetura Distribuída e Resiliência

**Equipe:**
| Nome | RM |
|---|---|
| Eduardo Borges | RM 369948 |
| Heber Mizuno | RM 369455 |
| Patrick Nascimento | RM 369393 |
| Rubens Neto | RM 370154 |

---

## Sobre este repositório

Este repositório é a continuação do projeto iniciado na Fase 2 e evoluído para a Fase 3 do Tech Challenge.

> 📄 **O README da Fase 2 está disponível na raiz do projeto com o nome [`README-v0.md`](./README-v0.md).**
> Nele estão documentados os endpoints de CRUD de usuários, restaurantes e cardápio desenvolvidos anteriormente.

---

## Padrão de Commits

Este projeto utilizou padrões de commit diferentes em cada fase:

**Fase 2** — commits seguindo o padrão com emoji do repositório:
> [https://github.com/iuricode/padroes-de-commits](https://github.com/iuricode/padroes-de-commits)

**Fase 3** — commits seguindo o padrão **Conventional Commits** sem emoji:
```
feat(escopo): descrição
fix(escopo): descrição
refactor(escopo): descrição
chore(escopo): descrição
docs(escopo): descrição
```

---

## Como executar

**Pré-requisito:** Docker Desktop instalado e em execução.

```bash
# Clone o repositório
git clone https://github.com/patrickiub/tech-challenge3
cd tech-challenge3

# Suba todos os serviços com um único comando
docker compose up --build
```

Todos os serviços sobem automaticamente:

| Serviço | URL |
|---|---|
| pedido-service | http://localhost:8080 |
| pagamento-service | http://localhost:8081 |
| procpag (mock externo) | http://localhost:8089 |

---

## Documentação completa

A documentação detalhada da Fase 3 está disponível em [`documentacao.md`](./documentacao.md), contendo:

- Descrição da arquitetura e diagrama de componentes
- Fluxo principal de funcionamento
- Descrição de todos os endpoints com exemplos
- Configuração do Docker Compose
- Pontos de resiliência (Circuit Breaker, Retry, Timeout, Fallback)
- Eventos Kafka utilizados
- Instruções de uso da collection Postman

---

## Repositório de código

https://github.com/patrickiub/tech-challenge3
