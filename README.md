![CI Pipeline](https://github.com/MariiMariis/ricknmorty-docker/actions/workflows/ci.yml/badge.svg)

# Rick and Morty API - Docker

API Spring Boot para consulta de personagens do universo Rick and Morty, containerizada com Docker.

## Stack

- Java 17 / Spring Boot 4.0.3
- PostgreSQL
- Docker / Docker Compose
- GitHub Actions (CI/CD)

## Como executar

```bash
docker-compose up --build
```

A API estará disponível em `http://localhost:8080`.

## Pipeline CI/CD

O projeto possui um pipeline de integração contínua configurado com GitHub Actions:

- **Lint (Checkstyle)**: verificação de estilo do código Java
- **Testes**: compilação e execução dos testes automatizados
- **Segurança (Trivy)**: análise de vulnerabilidades no código-fonte

O pipeline pode ser executado automaticamente (push/PR na branch `master`) ou manualmente pela aba Actions, com opções para habilitar/desabilitar testes e linting.

---

## Depuração de Erro no Pipeline

Para exercitar a depuração, foi introduzido intencionalmente um step com o comando `run: exit 1` no workflow `ci.yml`. Após o push, o pipeline falhou conforme esperado.

**Processo de identificação e correção:**

1. Acessei a aba **Actions** no repositório do GitHub
2. Cliquei na execução que apresentou falha (marcada com ícone vermelho ✗)
3. Naveguei até o job que falhou e expandi os logs do step problemático
4. O log exibiu claramente a mensagem `Process completed with exit code 1`, indicando o step exato da falha
5. Corrigi o arquivo `ci.yml` removendo o comando `exit 1`
6. Após o novo push, o pipeline executou com sucesso

As ferramentas utilizadas foram a interface web do GitHub Actions, especificamente a visualização de logs por step dentro de cada job.

---

## Observações sobre Modos de Execução 

Foram realizadas duas execuções distintas do pipeline:

1. **Push automático**: executado automaticamente ao enviar código para a branch `master`. Todos os jobs são executados com os parâmetros padrão (testes e lint habilitados).

2. **Execução manual (Run workflow)**: disparado pelo botão "Run workflow" na aba Actions. Permite escolher individualmente se deseja executar os testes e/ou o linting através dos parâmetros booleanos `run_tests` e `run_lint`.

A principal diferença é a **flexibilidade**: o push automático garante que toda alteração passe pela verificação completa, enquanto a execução manual permite ao desenvolvedor escolher quais verificações rodar, útil para diagnósticos rápidos ou re-execuções parciais. Na aba Actions, é possível filtrar as execuções por tipo de gatilho, diferenciando visualmente as execuções automáticas das manuais pela coluna de evento.
