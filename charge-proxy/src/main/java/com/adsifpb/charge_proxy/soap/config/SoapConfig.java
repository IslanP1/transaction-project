package com.adsifpb.charge_proxy.soap.config;

import com.adsifpb.charge_proxy.soap.CobrancaSoapServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do endpoint SOAP JAX-WS
 * Expõe o serviço de cobrança via protocolo SOAP-RPC
 */
@Configuration
public class SoapConfig {

    private final CobrancaSoapServiceImpl cobrancaSoapService;

    public SoapConfig(CobrancaSoapServiceImpl cobrancaSoapService) {
        this.cobrancaSoapService = cobrancaSoapService;
    }

    /**
     * Publica o endpoint SOAP
     * WSDL disponível em: http://localhost:8081/ws/cobranca?wsdl
     */
    @Bean
    public Endpoint cobrancaEndpoint() {
        String address = "http://0.0.0.0:8082/ws/cobranca";
        Endpoint endpoint = Endpoint.publish(address, cobrancaSoapService);
        return endpoint;
    }
}
