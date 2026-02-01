# Charge Proxy

## 📋 Descrição

O **Charge Proxy** é o módulo responsável pela comunicação direta com o gateway de pagamento **ASAAS**. Ele atua como intermediário entre o **Charge Manager** e a API do ASAAS.

## 🏗️ Arquitetura

```
┌─────────────────┐   SOAP/JAX-WS   ┌──────────────┐    HTTPS     ┌───────────────┐
│  Charge Manager │ ──────────────► │ Charge Proxy │ ◄──────────► │ ASAAS Gateway │
└─────────────────┘    (8082)       └──────────────┘              └───────────────┘
         ▲                                │
         │                                │ Webhook (REST)
         │ Observer Pattern               ▼
         │ (notification by event)  ┌──────────────┐
         └───────────────────────── │ ASAAS Webhook│
                                    │  (notifica)  │
                                    └──────────────┘
```

### Protocolos de Comunicação

| Direção | Protocolo | Porta | Descrição |
|---------|-----------|-------|-----------|
| Manager → Proxy | SOAP/RPC (JAX-WS) | 8082 | Criar/Cancelar/Buscar cobranças |
| ASAAS → Proxy | REST (Webhook) | 8081 | Notificações de status |
| Proxy → Manager | REST | 8080 | Atualização de status via Observer |
| Proxy → ASAAS | REST (OpenFeign) | HTTPS | Integração com API ASAAS |

## 🚀 Funcionalidades

### SOAP Endpoints (JAX-WS) - Porta 8082

O Charge Manager comunica-se com o Charge Proxy via protocolo SOAP-RPC.

**WSDL:** `http://localhost:8082/ws/cobranca?wsdl`

#### Operações Disponíveis:

| Operação | Descrição |
|----------|-----------|
| `criarCobranca` | Cria uma nova cobrança no ASAAS |
| `cancelarCobranca` | Cancela uma cobrança existente |
| `buscarCobranca` | Busca informações de uma cobrança |

#### Exemplo de Request SOAP:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:soap="http://soap.charge_proxy.adsifpb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:criarCobranca>
         <cobrancaRequest>
            <cobrancaId>1</cobrancaId>
            <clienteId>1</clienteId>
            <clienteNome>João Silva</clienteNome>
            <clienteCpfCnpj>12345678901</clienteCpfCnpj>
            <clienteEmail>joao@email.com</clienteEmail>
            <descricao>Mensalidade Janeiro</descricao>
            <valor>150.00</valor>
            <dataVencimento>2024-02-15</dataVencimento>
            <tipoPagamento>PIX</tipoPagamento>
         </cobrancaRequest>
      </soap:criarCobranca>
   </soapenv:Body>
</soapenv:Envelope>
```

### REST Endpoints - Porta 8081

#### 1. Criar Cobrança
```http
POST /api/cobranca
Content-Type: application/json

{
  "cobrancaId": 1,
  "clienteId": 1,
  "clienteNome": "João Silva",
  "clienteCpfCnpj": "12345678901",
  "clienteEmail": "joao@email.com",
  "descricao": "Mensalidade Janeiro",
  "valor": 150.00,
  "dataVencimento": "2024-02-15",
  "tipoPagamento": "PIX"
}
```

**Resposta:**
```json
{
  "asaasId": "pay_abc123",
  "status": "PENDING",
  "invoiceUrl": "https://...",
  "pixQrCodeUrl": "https://...",
  "sucesso": true,
  "mensagem": "Cobrança criada com sucesso no ASAAS"
}
```

#### 2. Cancelar Cobrança
```http
DELETE /api/cobranca/{asaasCobrancaId}
```

#### 3. Buscar Cobrança
```http
GET /api/cobranca/{asaasCobrancaId}
```

#### 4. Receber Webhook (chamado pelo ASAAS)
```http
POST /api/webhook/asaas
Content-Type: application/json

{
  "event": "PAYMENT_RECEIVED",
  "payment": {
    "id": "pay_abc123",
    "status": "RECEIVED",
    "value": 150.00,
    "externalReference": "1"
  }
}
```

#### 5. Health Check
```http
GET /api/health
GET /api/ping
GET /api/info
GET /api/webhook/asaas/health
```

## ⚙️ Configuração

### application.properties

```properties
# Porta do servidor
server.port=8081

# API ASAAS (Sandbox)
asaas.api.url=https://sandbox.asaas.com/api/v3
asaas.api.key=sua_api_key_aqui

# Charge Manager
charge-manager.api.url=http://localhost:8080
```

### Variáveis de Ambiente

```bash
# Para não expor a API Key no código
ASAAS_API_KEY=sua_api_key_do_asaas
```

## 🔑 Configurar ASAAS

### 1. Criar conta no ASAAS Sandbox
Acesse: https://sandbox.asaas.com/

### 2. Obter API Key
1. Faça login no ASAAS Sandbox
2. Vá em: **Configurações** → **Integrações** → **API**
3. Copie a API Key

### 3. Configurar Webhook
1. Vá em: **Configurações** → **Integrações** → **Webhooks**
2. Adicione um novo webhook:
   - URL: `http://seu-dominio:8081/api/webhook/asaas`
   - Eventos: Selecione os eventos de pagamento
   - Token: Configure um token de segurança (opcional)

**⚠️ Nota:** Para desenvolvimento local, você precisará expor sua porta 8081 usando ferramentas como **ngrok** ou **localtunnel**.

## 📊 Tipos de Pagamento

| Código | Tipo | Descrição |
|--------|------|-----------|
| PIX | PIX | Pagamento instantâneo |
| BOLETO | Boleto | Boleto bancário |
| CREDIT_CARD | Cartão de Crédito | Pagamento com cartão |

## 📈 Status das Cobranças

| Status ASAAS | ID Sistema | Descrição |
|--------------|------------|-----------|
| PENDING | 1 | Aguardando pagamento |
| RECEIVED | 4 | Pago |
| CONFIRMED | 4 | Pagamento confirmado |
| OVERDUE | 1 | Vencida |
| REFUNDED | 3 | Cancelada/Estornada |

## 🛠️ Tecnologias

- **Spring Boot 3.2**
- **Spring Cloud OpenFeign** - Cliente HTTP declarativo para ASAAS
- **JAX-WS / SOAP** - Comunicação RPC com Charge Manager
- **Jakarta XML Web Services** - Implementação SOAP
- **Java 17**
- **Docker / Docker Swarm** - Containerização e orquestração

## 📁 Estrutura do Projeto

```
charge-proxy/
├── Dockerfile
├── src/main/java/com/adsifpb/charge_proxy/
│   ├── ChargeProxyApplication.java
│   ├── ProxyController.java
│   ├── client/
│   │   ├── AsaasClient.java         # Feign client para ASAAS
│   │   ├── AsaasFeignConfig.java    # Configuração do Feign
│   │   └── ChargeManagerClient.java # Feign client para notificar Manager
│   ├── controller/
│   │   ├── CobrancaProxyController.java # Endpoints REST de cobrança
│   │   └── WebhookController.java       # Endpoint de webhook ASAAS
│   ├── soap/                            # Implementação JAX-WS
│   │   ├── CobrancaSoapService.java     # Interface SOAP
│   │   ├── CobrancaSoapServiceImpl.java # Implementação SOAP
│   │   ├── config/
│   │   │   └── SoapConfig.java          # Configuração endpoint SOAP
│   │   └── dto/
│   │       ├── CobrancaSoapRequest.java
│   │       └── CobrancaSoapResponse.java
│   ├── dto/
│   │   ├── CriarCobrancaRequest.java
│   │   ├── CriarCobrancaResponse.java
│   │   ├── AtualizarStatusRequest.java
│   │   └── asaas/
│   │       ├── AsaasCobrancaRequest.java
│   │       ├── AsaasCobrancaResponse.java
│   │       ├── AsaasClienteRequest.java
│   │       ├── AsaasClienteResponse.java
│   │       ├── AsaasClienteListResponse.java
│   │       └── AsaasWebhookEvent.java
│   └── service/
│       ├── AsaasService.java    # Lógica de integração com ASAAS
│       └── WebhookService.java  # Processa webhooks e notifica Manager
└── src/main/resources/
    ├── application.properties
    └── application-docker.properties
```

## 🧪 Testar Localmente

### 1. Iniciar o serviço
```bash
mvn spring-boot:run
```

### 2. Testar health check
```bash
curl http://localhost:8081/api/health
```

### 3. Criar cobrança (exemplo)
```bash
curl -X POST http://localhost:8081/api/cobranca \
  -H "Content-Type: application/json" \
  -d '{
    "cobrancaId": 1,
    "clienteId": 1,
    "clienteNome": "Teste",
    "clienteCpfCnpj": "12345678901",
    "clienteEmail": "teste@email.com",
    "descricao": "Teste",
    "valor": 100.00,
    "dataVencimento": "2024-12-31",
    "tipoPagamento": "PIX"
  }'
```

## 🐳 Docker Swarm

O serviço roda nas portas **8081** (REST) e **8082** (SOAP) no Docker Swarm.

### Construir a imagem
```bash
cd charge-proxy
mvn clean package -DskipTests
docker build -t charge-proxy:latest .
```

### Executar via Docker Swarm
```bash
docker stack deploy -c docker/docker-stack.yml transaction-app
```

### Configuração no docker-stack.yml
```yaml
charge-proxy:
  image: charge-proxy:latest
  ports:
    - "8081:8081"  # REST (webhooks)
    - "8082:8082"  # SOAP (JAX-WS)
  environment:
    - SPRING_PROFILES_ACTIVE=docker
    - ASAAS_API_KEY=sua_api_key
    - ASAAS_WEBHOOK_TOKEN=seu_token_webhook
    - CHARGE_MANAGER_URL=http://charge-manager:8080
  deploy:
    replicas: 1
    resources:
      limits:
        cpus: '0.5'
        memory: 512M
```

## 📝 Notas

- Em produção, use a URL: `https://www.asaas.com/api/v3`
- Sempre valide o token do webhook para segurança
- O `externalReference` é usado para vincular cobranças do ASAAS com o sistema local
