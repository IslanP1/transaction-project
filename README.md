# 💳 Transaction Project - Sistema de Gerenciamento de Cobranças

## 📋 Descrição

Sistema de gerenciamento de cobranças integrado com o gateway de pagamento **ASAAS**. O projeto segue uma arquitetura de microsserviços com dois módulos principais que se comunicam via protocolo **SOAP/JAX-WS**.

## 🏗️ Arquitetura

```
                                    ┌─────────────────────────────────────────┐
                                    │           Infraestrutura                │
                                    │  • Docker (3 imagens)                   │
                                    │  • Docker Swarm                         │
                                    │  • NÃO ADOTAR docker-compose            │
                                    └─────────────────────────────────────────┘

┌──────────────┐                                                              notification by webhook
│              │                                                                        │
│   Database   │◄────────────┐                                                          ▼
│  PostgreSQL  │             │                                              ┌───────────────────┐
│              │             │                                              │    <<ASAAS>>      │
└──────────────┘             │                                              │     Payment       │
                             │                                              │     Gateway       │
                    ┌────────┴────────┐      <<jaxws>>       ┌─────────────┐└───────────────────┘
                    │                 │      soap-rpc        │             │         ▲
                    │  Charge Manager │─────────────────────►│ Charge Proxy│─────────┘
                    │                 │       (8082)         │             │   HTTPS/REST
                    │   ┌─────────┐   │                      │   (8081)    │
                    │   │   api   │   │◄─────────────────────│   (8082)    │
                    │   ├─────────┤   │  <<observer pattern>>└─────────────┘
                    │   │business │   │  notification by event
                    │   ├─────────┤   │
                    │   │  infra  │   │
                    │   └─────────┘   │
                    │     (8080)      │
                    └─────────────────┘
```

### Módulos

| Módulo | Porta | Descrição |
|--------|-------|-----------|
| **Charge Manager** | 8080 | Responsável por criar, alterar e cancelar cobranças, notificar usuários via email e armazenar todas as informações pertinentes ao gerenciamento de cobranças |
| **Charge Proxy** | 8081 (REST), 8082 (SOAP) | Responsável pela comunicação direta com o sistema de pagamento, estabelecendo regras de negócio compatíveis com o sistema (não com o ASAAS) |
| **PostgreSQL** | 5432 | Banco de dados para persistência |

## 📌 Requisitos Funcionais

- **[f1]** Cadastro de Cliente
- **[f2]** Cadastro de Cobrança
- **[f3]** Gerar cobrança (tipo PIX, BOLETO, CARTÃO CRÉDITO)
- **[f4]** Atualizar o status da cobrança para PENDING, REGISTERED, CANCELED, PAID
- **[f5]** Enviar email para o cliente notificando as alterações dos status

## 📌 Requisitos Não Funcionais

- **[m1]** Persistência de Dados com uso de JDBC
- **[m2]** Uso de controle de transações explícitas
- **[m3]** Adoção de Arquitetura em 3 camadas
- **[m4]** Implementação do processo de criação de cobrança avulsa no ASAAS
- **[m5]** Implementação do processo de cancelamento de cobrança avulsa no ASAAS
- **[m6]** Implementação de uma configuração de webhook (adotar chave de segurança por Authentication Bearer)
- **[m7]** Armazenamento em PostgreSQL
- **[m8]** Notificar recebimento de "hook-event" com uso observer pattern

## 🛠️ Tecnologias

| Tecnologia | Uso |
|------------|-----|
| **Spring Framework** | Framework principal |
| **Spring Boot 3.2** | Configuração e inicialização |
| **Migrations Flyway** | Versionamento de banco de dados |
| **Java Mail** | Envio de emails |
| **OpenFeign (HttpClient)** | Cliente HTTP para ASAAS |
| **JAX-WS / SOAP** | Comunicação entre Manager e Proxy |
| **Tomcat (embedded)** | Servidor de aplicação |
| **PostgreSQL 15** | Banco de dados |
| **Docker / Docker Swarm** | Containerização e orquestração |
| **Java 17** | Linguagem de programação |

## 📁 Estrutura do Projeto

```
transaction-project/
├── charge-manager/          # Módulo de gerenciamento de cobranças
│   ├── src/
│   │   ├── main/java/       # Código fonte (3 camadas)
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/  # Scripts Flyway
│   ├── Dockerfile
│   └── pom.xml
│
├── charge-proxy/            # Módulo de comunicação com ASAAS
│   ├── src/
│   │   ├── main/java/
│   │   │   ├── client/      # Clientes Feign (ASAAS, ChargeManager)
│   │   │   ├── controller/  # Controllers REST
│   │   │   ├── soap/        # Serviços SOAP/JAX-WS
│   │   │   ├── service/     # Lógica de negócio
│   │   │   └── dto/         # Objetos de transferência
│   │   └── resources/
│   ├── Dockerfile
│   └── pom.xml
│
├── docker/
│   └── docker-stack.yml     # Configuração Docker Swarm
│
├── deploy.ps1               # Script de deploy (Windows)
├── deploy.sh                # Script de deploy (Linux/Mac)
├── teardown.ps1             # Script de teardown (Windows)
├── teardown.sh              # Script de teardown (Linux/Mac)
├── mvnw / mvnw.cmd          # Maven Wrapper
└── pom.xml                  # POM pai (multi-módulo)
```

## 🚀 Como Executar

### Pré-requisitos

- **Java 17+**
- **Maven 3.8+**
- **Docker** com suporte a **Swarm**
- Conta no **ASAAS Sandbox** (https://sandbox.asaas.com)

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd transaction-project
```

### 2. Configurar Variáveis de Ambiente

```bash
# Linux/Mac
export ASAAS_API_KEY=sua_api_key_do_asaas
export ASAAS_WEBHOOK_TOKEN=seu_token_de_seguranca

# Windows PowerShell
$env:ASAAS_API_KEY="sua_api_key_do_asaas"
$env:ASAAS_WEBHOOK_TOKEN="seu_token_de_seguranca"
```

### 3. Build do Projeto

```bash
./mvnw clean package -DskipTests
```

### 4. Build das Imagens Docker

```bash
docker build -t charge-manager:latest ./charge-manager
docker build -t charge-proxy:latest ./charge-proxy
```

### 5. Inicializar Docker Swarm

```bash
docker swarm init
```

### 6. Deploy da Stack

```bash
docker stack deploy -c docker/docker-stack.yml transaction-app
```

### Ou use o script de deploy:

```bash
# Windows
.\deploy.ps1

# Linux/Mac
./deploy.sh
```

## 🔧 Endpoints Disponíveis

### Charge Manager (porta 8080)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/cliente` | Lista todos os clientes |
| POST | `/api/cliente` | Cadastra novo cliente |
| GET | `/api/cliente/{id}` | Busca cliente por ID |
| GET | `/api/cobranca` | Lista todas as cobranças |
| POST | `/api/cobranca` | Cria nova cobrança |
| PATCH | `/api/cobranca/{id}` | Atualiza status da cobrança |

### Charge Proxy (porta 8081 - REST)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/health` | Health check |
| GET | `/api/ping` | Ping/Pong |
| GET | `/api/info` | Informações do serviço |
| POST | `/api/webhook/asaas` | Recebe webhooks do ASAAS |

### Charge Proxy (porta 8082 - SOAP)

| Operação | Descrição |
|----------|-----------|
| `criarCobranca` | Cria cobrança no ASAAS |
| `cancelarCobranca` | Cancela cobrança no ASAAS |
| `buscarCobranca` | Busca cobrança no ASAAS |

**WSDL:** `http://localhost:8082/ws/cobranca?wsdl`

## 📊 Fluxo de Cobrança

```
1. Cliente cadastrado no Charge Manager
                │
                ▼
2. Criar cobrança (Charge Manager)
                │
                ▼
3. Enviar para ASAAS via SOAP (Charge Proxy)
                │
                ▼
4. ASAAS cria a cobrança e retorna dados (PIX, Boleto, etc)
                │
                ▼
5. Cliente paga a cobrança
                │
                ▼
6. ASAAS notifica via Webhook (Charge Proxy)
                │
                ▼
7. Charge Proxy notifica Charge Manager (Observer Pattern)
                │
                ▼
8. Charge Manager atualiza status e envia email ao cliente
```

## 🔐 Configuração do ASAAS

### 1. Criar Conta Sandbox
Acesse: https://sandbox.asaas.com/

### 2. Obter API Key
1. Faça login
2. Vá em: **Configurações** → **Integrações** → **API**
3. Copie a API Key

### 3. Configurar Webhook
1. Vá em: **Configurações** → **Integrações** → **Webhooks**
2. Configure a URL: `http://seu-dominio:8081/api/webhook/asaas`
3. Defina um token de segurança

> ⚠️ **Nota:** Para desenvolvimento local, use **ngrok** ou **localtunnel** para expor a porta 8081.

## 🐳 Comandos Docker Úteis

```bash
# Ver serviços rodando
docker service ls

# Ver logs de um serviço
docker service logs transaction-app_charge-manager
docker service logs transaction-app_charge-proxy

# Escalar serviço
docker service scale transaction-app_charge-proxy=3

# Remover stack
docker stack rm transaction-app

# Remover swarm
docker swarm leave --force
```

## 📝 Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `ASAAS_API_KEY` | API Key do ASAAS | - |
| `ASAAS_WEBHOOK_TOKEN` | Token de segurança do webhook | - |
| `SPRING_DATASOURCE_URL` | URL do banco PostgreSQL | jdbc:postgresql://localhost:5432/transaction_db |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | postgres |
| `CHARGE_MANAGER_URL` | URL do Charge Manager | http://localhost:8080 |
| `CHARGE_PROXY_SOAP_URL` | URL SOAP do Charge Proxy | http://localhost:8082/ws/cobranca |

## 📅 Iterações de Entregas

| Iteração | Data | Descrição |
|----------|------|-----------|
| **I1** | 01/12 | Todos os módulos ativados via docker com uma rota funcional passando por todos os módulos e camadas indo até o BD |
| **I2** | 19/12 | Comunicação o ASAAS e disponibilização webhook funcional |
| **I3** | 26/01 | Implementação das regras de negócio no manager e interação com os demais componentes/módulos |

## 👥 Equipe

- **ADSIFPB** - Instituto Federal da Paraíba

## 📄 Licença

Este projeto é desenvolvido para fins educacionais na disciplina de DAC (Desenvolvimento de Aplicações Corporativas).

---

⭐ **Dica:** Para testar localmente sem Docker, execute cada módulo separadamente com `mvn spring-boot:run` e configure o PostgreSQL manualmente.
