#!/bin/bash

# Build Maven
./mvnw clean package -DskipTests

# Build Docker Images
docker build -t charge-manager:latest ./charge-manager
docker build -t charge-proxy:latest ./charge-proxy

# Init Swarm
docker swarm init || echo "Swarm already initialized or failed"

# Create Network
docker network create --driver overlay transaction-net || echo "Network might already exist"

# Create Postgres Service
docker service create --name postgres --network transaction-net --publish 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=transaction_db postgres:15-alpine

# Create Charge Manager Service
docker service create --name charge-manager --network transaction-net --publish 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/transaction_db charge-manager:latest

# Create Charge Proxy Service
docker service create --name charge-proxy --network transaction-net --publish 8081:8081 charge-proxy:latest

