package com.adsifpb.charge_proxy.client;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Feign Client para ASAAS
 * Adiciona o header de autenticação em todas as requisições
 */
@Configuration
public class AsaasFeignConfig {

    @Value("${asaas.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor asaasRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("access_token", apiKey);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
