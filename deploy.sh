#!/bin/bash

# =============================================
# Script de Deploy - Transaction Project
# Docker Swarm
# =============================================

set -e

echo "================================================"
echo "  Transaction Project - Deploy Script"
echo "================================================"

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Verificar pré-requisitos
echo -e "\n${YELLOW}[1/7] Verificando pré-requisitos...${NC}"

# Verificar Java
if ! command -v java &> /dev/null; then
    echo -e "${RED}ERRO: Java não encontrado. Instale o JDK 17+.${NC}"
    exit 1
fi
echo -e "${GREEN}Java: OK${NC}"

# Verificar Docker
if ! docker info &> /dev/null; then
    echo -e "${RED}ERRO: Docker não está rodando.${NC}"
    exit 1
fi
echo -e "${GREEN}Docker: OK${NC}"

# Build Maven
echo -e "\n${YELLOW}[2/7] Compilando projeto Maven...${NC}"
./mvnw clean package -DskipTests
echo -e "${GREEN}Build Maven: OK${NC}"

# Build Docker Images
echo -e "\n${YELLOW}[3/7] Construindo imagens Docker...${NC}"
docker build -t charge-manager:latest ./charge-manager
docker build -t charge-proxy:latest ./charge-proxy
echo -e "${GREEN}Imagens Docker: OK${NC}"

# Init Swarm
echo -e "\n${YELLOW}[4/7] Inicializando Docker Swarm...${NC}"
docker swarm init 2>/dev/null || echo "Swarm já inicializado"
echo -e "${GREEN}Docker Swarm: OK${NC}"

# Deploy Stack
echo -e "\n${YELLOW}[5/7] Removendo stack anterior (se existir)...${NC}"
docker stack rm transaction-app 2>/dev/null || true
sleep 5

echo -e "\n${YELLOW}[6/7] Fazendo deploy da stack...${NC}"
docker stack deploy -c docker/docker-stack.yml transaction-app
echo -e "${GREEN}Deploy: OK${NC}"

# Aguardar serviços iniciarem
echo -e "\n${YELLOW}[7/7] Aguardando serviços iniciarem...${NC}"
sleep 30

# Mostrar status
echo -e "\n${CYAN}================================================${NC}"
echo -e "${CYAN}  Deploy concluído!${NC}"
echo -e "${CYAN}================================================${NC}"
echo -e "\nServiços:"
docker service ls

echo -e "\n${GREEN}Endpoints disponíveis:${NC}"
echo "  - Charge Manager:  http://localhost:8080"
echo "  - Charge Proxy:    http://localhost:8081 (REST)"
echo "  - Charge Proxy:    http://localhost:8082 (SOAP)"
echo "  - PostgreSQL:      localhost:5432"
echo -e "\n${YELLOW}WSDL SOAP: http://localhost:8082/ws/cobranca?wsdl${NC}"

echo -e "\nPara ver logs:"
echo "  docker service logs transaction-app_charge-manager -f"
echo "  docker service logs transaction-app_charge-proxy -f"
