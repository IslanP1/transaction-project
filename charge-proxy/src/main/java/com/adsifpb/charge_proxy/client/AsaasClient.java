package com.adsifpb.charge_proxy.client;

import com.adsifpb.charge_proxy.dto.asaas.AsaasClienteListResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasClienteRequest;
import com.adsifpb.charge_proxy.dto.asaas.AsaasClienteResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasCobrancaRequest;
import com.adsifpb.charge_proxy.dto.asaas.AsaasCobrancaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Cliente Feign para comunicação com a API do ASAAS
 * Documentação: https://docs.asaas.com/
 */
@FeignClient(
    name = "asaas-client",
    url = "${asaas.api.url}",
    configuration = AsaasFeignConfig.class
)
public interface AsaasClient {

    // =============================================
    // Endpoints de Cobrança (Payments)
    // =============================================

    /**
     * Criar uma nova cobrança
     */
    @PostMapping("/payments")
    AsaasCobrancaResponse criarCobranca(@RequestBody AsaasCobrancaRequest request);

    /**
     * Buscar cobrança por ID
     */
    @GetMapping("/payments/{id}")
    AsaasCobrancaResponse buscarCobranca(@PathVariable("id") String id);

    /**
     * Cancelar (deletar) uma cobrança
     */
    @DeleteMapping("/payments/{id}")
    AsaasCobrancaResponse cancelarCobranca(@PathVariable("id") String id);

    // =============================================
    // Endpoints de Cliente (Customers)
    // =============================================

    /**
     * Criar um novo cliente
     */
    @PostMapping("/customers")
    AsaasClienteResponse criarCliente(@RequestBody AsaasClienteRequest request);

    /**
     * Buscar cliente por ID
     */
    @GetMapping("/customers/{id}")
    AsaasClienteResponse buscarCliente(@PathVariable("id") String id);

    /**
     * Buscar cliente por CPF/CNPJ
     */
    @GetMapping("/customers")
    AsaasClienteListResponse buscarClientePorCpfCnpj(@RequestParam("cpfCnpj") String cpfCnpj);
}
