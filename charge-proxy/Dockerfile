FROM eclipse-temurin:17-jdk-alpine

# Metadados da imagem
LABEL maintainer="ADSIFPB"
LABEL description="Charge Proxy - Comunicação com ASAAS Payment Gateway via REST e SOAP"

# Variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Diretório de trabalho
WORKDIR /app

# Volume para logs
VOLUME /tmp

# Copia o JAR da aplicação
COPY target/*.jar app.jar

# Expõe as portas REST (8081) e SOAP (8082)
EXPOSE 8081 8082

# Healthcheck para verificar se a aplicação está saudável
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8081/api/health || exit 1

# Comando de inicialização
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
