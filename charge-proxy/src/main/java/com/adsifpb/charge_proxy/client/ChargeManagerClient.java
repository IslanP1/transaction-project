package com.adsifpb.charge_proxy.client;

import com.adsifpb.charge_proxy.dto.AtualizarStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Cliente Feign para comunicação com o Charge Manager
 * Usado para notificar mudanças de status via webhook
 */
@FeignClient(
    name = "charge-manager-client",
    url = "${charge-manager.api.url}"
)
public interface ChargeManagerClient {

    /**
     * Notifica o Charge Manager sobre atualização de status de cobrança
     */
    @PatchMapping("/api/cobranca/{cobrancaId}")
    void atualizarStatusCobranca(
        @PathVariable("cobrancaId") Long cobrancaId,
        @RequestParam("clienteId") Long clienteId,
        @RequestBody AtualizarStatusRequest request
    );
}
