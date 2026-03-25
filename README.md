![CI Pipeline](https://github.com/MariiMariis/ricknmorty-docker/actions/workflows/ci.yml/badge.svg?branch=master)

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

---

## TP3 — Pipeline Avançado

### Etapa 1 — Runner Auto-Hospedado

Configurado um runner auto-hospedado no repositório. O workflow `self-hosted-runner.yml` executa comandos básicos (`uname -a`, `java -version`) e instala software adicional (`curl`) durante a execução.

Para reexecutar: aba **Actions → Self-Hosted Runner → Run workflow**.

### Etapa 2 — Variáveis e Secrets

Criadas duas variáveis de repositório (`APP_MODE`, `SUPPORT_EMAIL`) e um secret (`PROD_TOKEN`). O workflow `vars-secrets-demo.yml` demonstra o acesso via `${{ vars.NOME }}` e `${{ secrets.NOME }}`, simulando uma autenticação protegida.

Para reexecutar: aba **Actions → Variáveis e Secrets → Run workflow**.

### Etapa 3 — Contextos e Escopos de Variáveis

O workflow `env-context-demo.yml` exibe informações dos contextos `github` (`actor`, `repository`, `event_name`) e `runner` (`os`, `arch`), além de demonstrar variáveis de ambiente configuradas em três níveis: workflow, job e step, mostrando como o escopo do step sobrescreve o do workflow.

Para reexecutar: aba **Actions → Env Context Demo → Run workflow**.

### Etapa 4 — Permissões e GITHUB_TOKEN

O workflow `auto-issue.yml` configura `permissions: issues: write` e utiliza o `GITHUB_TOKEN` para criar automaticamente uma issue no repositório caso uma variável obrigatória esteja ausente. O token é passado via `env: GH_TOKEN: ${{ secrets.GITHUB_TOKEN }}` para uso com o CLI `gh`.

Para reexecutar: aba **Actions → Auto Issue → Run workflow**.

### Etapa 5 — Ambientes de Deploy (Dev e Prod)

Configurados dois environments no GitHub: `dev` (liberação automática) e `prod` (com exigência de aprovação manual). O workflow `deploy.yml` dispara deploy em `dev` ao fazer push na branch `dev` e deploy em `prod` ao fazer push na branch `master`.

### Etapa 6 — Nova Funcionalidade na API

Adicionado o endpoint `GET /api/characters/search?name=Rick` para busca de personagens por nome. A busca é case-insensitive e retorna uma lista de personagens cujo nome contenha o termo informado.

Testes unitários foram implementados em `CharacterControllerTest.java` utilizando MockMvc para validar o endpoint de busca com resultados e sem resultados.

O pipeline de integração contínua permanece funcional após a adição.
