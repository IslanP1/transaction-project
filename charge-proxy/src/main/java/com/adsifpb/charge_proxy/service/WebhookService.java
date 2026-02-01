package com.adsifpb.charge_proxy.service;

import com.adsifpb.charge_proxy.client.ChargeManagerClient;
import com.adsifpb.charge_proxy.dto.AtualizarStatusRequest;
import com.adsifpb.charge_proxy.dto.asaas.AsaasWebhookEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por processar webhooks do ASAAS
 * e notificar o Charge Manager sobre mudanças de status
 */
@Service
public class WebhookService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);

    private final ChargeManagerClient chargeManagerClient;

    public WebhookService(ChargeManagerClient chargeManagerClient) {
        this.chargeManagerClient = chargeManagerClient;
    }

    /**
     * Processa um evento de webhook do ASAAS
     */
    public void processarWebhook(AsaasWebhookEvent webhookEvent) {
        logger.info("Processando webhook do ASAAS. Evento: {}", webhookEvent.getEvent());

        if (webhookEvent.getPayment() == null) {
            logger.warn("Webhook sem dados de pagamento, ignorando...");
            return;
        }

        String evento = webhookEvent.getEvent();
        String statusAsaas = webhookEvent.getPayment().getStatus();
        String externalReference = webhookEvent.getPayment().getExternalReference();

        logger.info("Evento: {}, Status ASAAS: {}, External Reference: {}",
                    evento, statusAsaas, externalReference);

        // Extrai o ID da cobrança e do cliente do externalReference
        // Formato esperado: "cobrancaId" ou "cobrancaId:clienteId"
        Long cobrancaId = extrairCobrancaId(externalReference);
        Long clienteId = extrairClienteId(externalReference, webhookEvent);

        if (cobrancaId == null) {
            logger.error("Não foi possível extrair o ID da cobrança do externalReference: {}", externalReference);
            return;
        }

        // Mapeia o status do ASAAS para o status do nosso sistema
        Long statusId = mapStatusAsaasParaSistema(statusAsaas);

        if (statusId != null) {
            notificarChargeManager(cobrancaId, clienteId, statusId);
        }
    }

    /**
     * Notifica o Charge Manager sobre mudança de status
     */
    private void notificarChargeManager(Long cobrancaId, Long clienteId, Long statusId) {
        try {
            logger.info("Notificando Charge Manager: cobrancaId={}, clienteId={}, statusId={}",
                        cobrancaId, clienteId, statusId);

            AtualizarStatusRequest request = new AtualizarStatusRequest(statusId);
            chargeManagerClient.atualizarStatusCobranca(cobrancaId, clienteId, request);

            logger.info("Charge Manager notificado com sucesso!");

        } catch (Exception e) {
            logger.error("Erro ao notificar Charge Manager: {}", e.getMessage(), e);
        }
    }

    /**
     * Mapeia o status do ASAAS para o ID de status do nosso sistema
     *
     * Status ASAAS:
     * - PENDING: Aguardando pagamento
     * - RECEIVED: Recebida (saldo já creditado)
     * - CONFIRMED: Pagamento confirmado
     * - OVERDUE: Vencida
     * - REFUNDED: Estornada
     * - RECEIVED_IN_CASH: Recebida em dinheiro
     * - REFUND_REQUESTED: Estorno solicitado
     * - CHARGEBACK_REQUESTED: Recebido chargeback
     * - CHARGEBACK_DISPUTE: Em disputa de chargeback
     * - AWAITING_CHARGEBACK_REVERSAL: Aguardando cancelamento do chargeback
     * - DUNNING_REQUESTED: Em processo de negativação
     * - DUNNING_RECEIVED: Recuperada
     * - AWAITING_RISK_ANALYSIS: Em análise de risco
     *
     * Mapeamento para o sistema:
     * 1 = PENDING
     * 2 = REGISTERED (equivalente a PENDING no ASAAS após criação)
     * 3 = CANCELED
     * 4 = PAID (equivalente a RECEIVED/CONFIRMED no ASAAS)
     */
    private Long mapStatusAsaasParaSistema(String statusAsaas) {
        if (statusAsaas == null) {
            return null;
        }

        return switch (statusAsaas.toUpperCase()) {
            case "PENDING" -> 1L;                    // PENDING
            case "RECEIVED", "CONFIRMED",
                 "RECEIVED_IN_CASH" -> 4L;           // PAID
            case "OVERDUE" -> 1L;                    // Mantém como PENDING mas vencido
            case "REFUNDED", "REFUND_REQUESTED",
                 "CHARGEBACK_REQUESTED",
                 "CHARGEBACK_DISPUTE" -> 3L;         // CANCELED
            default -> null;
        };
    }

    /**
     * Extrai o ID da cobrança do externalReference
     */
    private Long extrairCobrancaId(String externalReference) {
        if (externalReference == null || externalReference.isEmpty()) {
            return null;
        }

        try {
            // Se contiver ":", pega a primeira parte
            if (externalReference.contains(":")) {
                return Long.parseLong(externalReference.split(":")[0]);
            }
            return Long.parseLong(externalReference);
        } catch (NumberFormatException e) {
            logger.error("Erro ao converter externalReference para Long: {}", externalReference);
            return null;
        }
    }

    /**
     * Extrai o ID do cliente do externalReference ou do webhook
     */
    private Long extrairClienteId(String externalReference, AsaasWebhookEvent webhookEvent) {
        // Tenta extrair do externalReference (formato: cobrancaId:clienteId)
        if (externalReference != null && externalReference.contains(":")) {
            try {
                return Long.parseLong(externalReference.split(":")[1]);
            } catch (Exception e) {
                logger.warn("Não foi possível extrair clienteId do externalReference");
            }
        }

        // Por padrão, retorna 1 (será necessário ajustar conforme a lógica do sistema)
        return 1L;
    }
}
