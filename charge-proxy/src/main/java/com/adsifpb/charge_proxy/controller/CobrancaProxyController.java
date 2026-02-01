package com.adsifpb.charge_proxy.controller;

import com.adsifpb.charge_proxy.dto.CriarCobrancaRequest;
import com.adsifpb.charge_proxy.dto.CriarCobrancaResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasCobrancaResponse;
import com.adsifpb.charge_proxy.service.AsaasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para operações de cobrança no ASAAS
 * Recebe requisições do Charge Manager e repassa para o ASAAS
 */
@RestController
@RequestMapping("/api/cobranca")
public class CobrancaProxyController {

    private static final Logger logger = LoggerFactory.getLogger(CobrancaProxyController.class);

    private final AsaasService asaasService;

    public CobrancaProxyController(AsaasService asaasService) {
        this.asaasService = asaasService;
    }

    /**
     * Cria uma cobrança no ASAAS
     * Endpoint chamado pelo Charge Manager
     */
    @PostMapping
    public ResponseEntity<CriarCobrancaResponse> criarCobranca(@RequestBody CriarCobrancaRequest request) {
        logger.info("Recebida requisição para criar cobrança. CobrancaId: {}, ClienteId: {}",
                    request.getCobrancaId(), request.getClienteId());

        CriarCobrancaResponse response = asaasService.criarCobranca(request);

        if (response.isSucesso()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Cancela uma cobrança no ASAAS
     */
    @DeleteMapping("/{asaasCobrancaId}")
    public ResponseEntity<Void> cancelarCobranca(@PathVariable String asaasCobrancaId) {
        logger.info("Recebida requisição para cancelar cobrança. AsaasId: {}", asaasCobrancaId);

        boolean cancelada = asaasService.cancelarCobranca(asaasCobrancaId);

        if (cancelada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Busca uma cobrança no ASAAS
     */
    @GetMapping("/{asaasCobrancaId}")
    public ResponseEntity<AsaasCobrancaResponse> buscarCobranca(@PathVariable String asaasCobrancaId) {
        logger.info("Buscando cobrança no ASAAS. AsaasId: {}", asaasCobrancaId);

        AsaasCobrancaResponse response = asaasService.buscarCobranca(asaasCobrancaId);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
