# =============================================
# Script de Deploy - Transaction Project
# Docker Swarm
# =============================================

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "  Transaction Project - Deploy Script" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan

# Verificar pré-requisitos
Write-Host "`n[1/7] Verificando pré-requisitos..." -ForegroundColor Yellow

# Verificar Java
$javaVersion = & cmd /c "java -version 2>&1" | Select-Object -First 1
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERRO: Java não encontrado. Instale o JDK 17+." -ForegroundColor Red
    exit 1
}
Write-Host "Java: OK" -ForegroundColor Green

# Verificar Docker
$dockerVersion = docker version --format '{{.Server.Version}}' 2>$null
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERRO: Docker não está rodando." -ForegroundColor Red
    exit 1
}
Write-Host "Docker: OK (v$dockerVersion)" -ForegroundColor Green

# Build Maven
Write-Host "`n[2/7] Compilando projeto Maven..." -ForegroundColor Yellow
./mvnw clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERRO: Falha no build Maven." -ForegroundColor Red
    exit 1
}
Write-Host "Build Maven: OK" -ForegroundColor Green

# Build Docker Images
Write-Host "`n[3/7] Construindo imagens Docker..." -ForegroundColor Yellow
docker build -t charge-manager:latest ./charge-manager
docker build -t charge-proxy:latest ./charge-proxy
Write-Host "Imagens Docker: OK" -ForegroundColor Green

# Init Swarm
Write-Host "`n[4/7] Inicializando Docker Swarm..." -ForegroundColor Yellow
$swarmStatus = docker info --format '{{.Swarm.LocalNodeState}}' 2>$null
if ($swarmStatus -ne "active") {
    docker swarm init 2>$null
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Aviso: Swarm pode já estar inicializado" -ForegroundColor Yellow
    }
}
Write-Host "Docker Swarm: OK" -ForegroundColor Green

# Deploy Stack
Write-Host "`n[5/7] Removendo stack anterior (se existir)..." -ForegroundColor Yellow
docker stack rm transaction-app 2>$null
Start-Sleep -Seconds 5

Write-Host "`n[6/7] Fazendo deploy da stack..." -ForegroundColor Yellow
docker stack deploy -c docker/docker-stack.yml transaction-app
Write-Host "Deploy: OK" -ForegroundColor Green

# Aguardar serviços iniciarem
Write-Host "`n[7/7] Aguardando serviços iniciarem..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Mostrar status
Write-Host "`n================================================" -ForegroundColor Cyan
Write-Host "  Deploy concluído!" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "`nServiços:" -ForegroundColor White
docker service ls

Write-Host "`nEndpoints disponíveis:" -ForegroundColor White
Write-Host "  - Charge Manager:  http://localhost:8080" -ForegroundColor Green
Write-Host "  - Charge Proxy:    http://localhost:8081 (REST)" -ForegroundColor Green
Write-Host "  - Charge Proxy:    http://localhost:8082 (SOAP)" -ForegroundColor Green
Write-Host "  - PostgreSQL:      localhost:5432" -ForegroundColor Green
Write-Host "`nWSDL SOAP: http://localhost:8082/ws/cobranca?wsdl" -ForegroundColor Yellow

Write-Host "`nPara ver logs:" -ForegroundColor White
Write-Host "  docker service logs transaction-app_charge-manager -f" -ForegroundColor Gray
Write-Host "  docker service logs transaction-app_charge-proxy -f" -ForegroundColor Gray
