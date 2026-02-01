package com.adsifpb.charge_proxy.controller;

import com.adsifpb.charge_proxy.dto.asaas.AsaasWebhookEvent;
import com.adsifpb.charge_proxy.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para receber webhooks do ASAAS
 * O ASAAS envia notificações quando o status de uma cobrança muda
 * Documentação dos webhooks do ASAAS:
 * https://sandbox.asaas.com/ -> Configurações -> Integrações -> Webhooks
 * URL: http://dominio:8081/api/webhook/asaas
 */
@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    /**
     * Recebe webhooks do ASAAS
     *
     * Eventos possíveis:
     * - PAYMENT_CREATED: Cobrança criada
     * - PAYMENT_UPDATED: Cobrança atualizada
     * - PAYMENT_CONFIRMED: Pagamento confirmado
     * - PAYMENT_RECEIVED: Pagamento recebido
     * - PAYMENT_OVERDUE: Cobrança vencida
     * - PAYMENT_DELETED: Cobrança deletada
     * - PAYMENT_REFUNDED: Pagamento estornado
     */
    @PostMapping("/asaas")
    public ResponseEntity<String> receberWebhookAsaas(
            @RequestBody AsaasWebhookEvent webhookEvent,
            @RequestHeader(value = "asaas-access-token", required = false) String accessToken
    ) {
        logger.info("Webhook recebido do ASAAS. Evento: {}", webhookEvent.getEvent());

        // TODO: Validar o access token do webhook para segurança
        // if (!validarAccessToken(accessToken)) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        // }

        try {
            webhookService.processarWebhook(webhookEvent);
            return ResponseEntity.ok("Webhook processado com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao processar webhook: {}", e.getMessage(), e);
            // Retornamos 200 mesmo com erro para o ASAAS não reenviar
            // O erro é logado para análise posterior
            return ResponseEntity.ok("Webhook recebido (com erro no processamento)");
        }
    }

    /**
     * Endpoint para testar se o webhook está funcionando
     */
    @GetMapping("/asaas/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Webhook endpoint está ativo!");
    }
}
