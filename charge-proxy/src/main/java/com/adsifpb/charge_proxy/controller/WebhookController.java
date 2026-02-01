package com.adsifpb.charge_proxy.controller;

import com.adsifpb.charge_proxy.dto.asaas.AsaasWebhookEvent;
import com.adsifpb.charge_proxy.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para receber webhooks do ASAAS
 * O ASAAS envia notificações quando o status de uma cobrança muda
 * Configuração do webhook no ASAAS:
 * URL: http://dominio:8081/api/webhook/asaas
 */
@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final WebhookService webhookService;

    @Value("${asaas.webhook.token:}")
    private String webhookToken;

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

        // Valida o access token do webhook para segurança
        if (!validarAccessToken(accessToken)) {
            logger.warn("Token de webhook inválido ou ausente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

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
     * Valida o token de segurança do webhook
     * Se o token não estiver configurado, aceita todas as requisições
     */
    private boolean validarAccessToken(String accessToken) {
        // Se não há token configurado, aceita qualquer requisição (desenvolvimento)
        if (webhookToken == null || webhookToken.isEmpty()) {
            logger.debug("Token de webhook não configurado, aceitando requisição");
            return true;
        }

        // Valida o token recebido
        if (accessToken == null || accessToken.isEmpty()) {
            return false;
        }

        return webhookToken.equals(accessToken);
    }

    /**
     * Endpoint para testar se o webhook está funcionando
     */
    @GetMapping("/asaas/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Webhook endpoint está ativo!");
    }
}
