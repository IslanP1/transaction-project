package com.adsifpb.charge_proxy.service;

import com.adsifpb.charge_proxy.client.AsaasClient;
import com.adsifpb.charge_proxy.dto.CriarCobrancaRequest;
import com.adsifpb.charge_proxy.dto.CriarCobrancaResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasClienteListResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasClienteRequest;
import com.adsifpb.charge_proxy.dto.asaas.AsaasClienteResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasCobrancaRequest;
import com.adsifpb.charge_proxy.dto.asaas.AsaasCobrancaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * Serviço responsável pela comunicação com o ASAAS
 * Implementa a criação e cancelamento de cobranças
 */
@Service
public class AsaasService {

    private static final Logger logger = LoggerFactory.getLogger(AsaasService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final AsaasClient asaasClient;

    public AsaasService(AsaasClient asaasClient) {
        this.asaasClient = asaasClient;
    }

    /**
     * Cria uma cobrança no ASAAS
     */
    public CriarCobrancaResponse criarCobranca(CriarCobrancaRequest request) {
        try {
            logger.info("Criando cobrança no ASAAS para cliente: {}", request.getClienteId());

            // Primeiro, verifica/cria o cliente no ASAAS
            String clienteAsaasId = obterOuCriarCliente(request);

            // Cria a cobrança
            AsaasCobrancaRequest asaasRequest = new AsaasCobrancaRequest();
            asaasRequest.setCustomerId(clienteAsaasId);
            asaasRequest.setBillingType(mapTipoPagamento(request.getTipoPagamento()));
            asaasRequest.setValue(request.getValor());
            asaasRequest.setDueDate(request.getDataVencimento().format(DATE_FORMATTER));
            asaasRequest.setDescription(request.getDescricao());
            asaasRequest.setExternalReference(String.valueOf(request.getCobrancaId()));

            AsaasCobrancaResponse asaasResponse = asaasClient.criarCobranca(asaasRequest);

            logger.info("Cobrança criada no ASAAS com ID: {}", asaasResponse.getId());

            return CriarCobrancaResponse.sucesso(
                asaasResponse.getId(),
                asaasResponse.getStatus(),
                asaasResponse.getInvoiceUrl(),
                asaasResponse.getBankSlipUrl(),
                asaasResponse.getPixQrCodeUrl()
            );

        } catch (Exception e) {
            logger.error("Erro ao criar cobrança no ASAAS: {}", e.getMessage(), e);
            return CriarCobrancaResponse.erro("Erro ao criar cobrança: " + e.getMessage());
        }
    }

    /**
     * Cancela uma cobrança no ASAAS
     */
    public boolean cancelarCobranca(String asaasCobrancaId) {
        try {
            logger.info("Cancelando cobrança no ASAAS: {}", asaasCobrancaId);

            AsaasCobrancaResponse response = asaasClient.cancelarCobranca(asaasCobrancaId);

            logger.info("Cobrança cancelada com sucesso. Deleted: {}", response.getDeleted());
            return response.getDeleted() != null && response.getDeleted();

        } catch (Exception e) {
            logger.error("Erro ao cancelar cobrança no ASAAS: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Busca uma cobrança no ASAAS
     */
    public AsaasCobrancaResponse buscarCobranca(String asaasCobrancaId) {
        try {
            return asaasClient.buscarCobranca(asaasCobrancaId);
        } catch (Exception e) {
            logger.error("Erro ao buscar cobrança no ASAAS: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Obtém o ID do cliente no ASAAS ou cria um novo
     */
    private String obterOuCriarCliente(CriarCobrancaRequest request) {
        // Se já tem o ID do ASAAS, usa ele
        if (request.getClienteAsaasId() != null && !request.getClienteAsaasId().isEmpty()) {
            return request.getClienteAsaasId();
        }

        // Tenta buscar pelo CPF/CNPJ
        if (request.getClienteCpfCnpj() != null) {
            try {
                AsaasClienteListResponse clientes = asaasClient.buscarClientePorCpfCnpj(request.getClienteCpfCnpj());
                if (clientes.getData() != null && !clientes.getData().isEmpty()) {
                    return clientes.getData().get(0).getId();
                }
            } catch (Exception e) {
                logger.warn("Cliente não encontrado no ASAAS, será criado um novo");
            }
        }

        // Cria o cliente no ASAAS
        AsaasClienteRequest clienteRequest = new AsaasClienteRequest();
        clienteRequest.setName(request.getClienteNome());
        clienteRequest.setCpfCnpj(request.getClienteCpfCnpj());
        clienteRequest.setEmail(request.getClienteEmail());
        clienteRequest.setExternalReference(String.valueOf(request.getClienteId()));

        AsaasClienteResponse clienteResponse = asaasClient.criarCliente(clienteRequest);

        logger.info("Cliente criado no ASAAS com ID: {}", clienteResponse.getId());
        return clienteResponse.getId();
    }

    /**
     * Mapeia o tipo de pagamento do sistema para o formato do ASAAS
     */
    private String mapTipoPagamento(String tipoPagamento) {
        if (tipoPagamento == null) {
            return "BOLETO"; // Padrão
        }

        return switch (tipoPagamento.toUpperCase()) {
            case "PIX" -> "PIX";
            case "CARTAO", "CARTAO_CREDITO", "CREDIT_CARD" -> "CREDIT_CARD";
            case "BOLETO" -> "BOLETO";
            default -> "BOLETO";
        };
    }
}
