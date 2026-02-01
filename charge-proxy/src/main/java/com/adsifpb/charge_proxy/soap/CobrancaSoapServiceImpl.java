package com.adsifpb.charge_proxy.soap;

import com.adsifpb.charge_proxy.dto.CriarCobrancaRequest;
import com.adsifpb.charge_proxy.dto.CriarCobrancaResponse;
import com.adsifpb.charge_proxy.dto.asaas.AsaasCobrancaResponse;
import com.adsifpb.charge_proxy.service.AsaasService;
import com.adsifpb.charge_proxy.soap.dto.CobrancaSoapRequest;
import com.adsifpb.charge_proxy.soap.dto.CobrancaSoapResponse;
import jakarta.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Implementação do serviço SOAP para comunicação com Charge Manager
 * Utiliza protocolo SOAP-RPC conforme especificação JAX-WS
 */
@Service
@WebService(
    serviceName = "CobrancaService",
    portName = "CobrancaPort",
    targetNamespace = "http://soap.charge_proxy.adsifpb.com/",
    endpointInterface = "com.adsifpb.charge_proxy.soap.CobrancaSoapService"
)
public class CobrancaSoapServiceImpl implements CobrancaSoapService {

    private static final Logger logger = LoggerFactory.getLogger(CobrancaSoapServiceImpl.class);

    private final AsaasService asaasService;

    public CobrancaSoapServiceImpl(AsaasService asaasService) {
        this.asaasService = asaasService;
    }

    @Override
    public CobrancaSoapResponse criarCobranca(CobrancaSoapRequest request) {
        logger.info("[SOAP] Recebida requisição para criar cobrança. CobrancaId: {}, ClienteId: {}",
                    request.getCobrancaId(), request.getClienteId());

        try {
            // Converte a requisição SOAP para o formato interno
            CriarCobrancaRequest internalRequest = convertToInternalRequest(request);

            // Chama o serviço de integração com ASAAS
            CriarCobrancaResponse internalResponse = asaasService.criarCobranca(internalRequest);

            // Converte a resposta para formato SOAP
            CobrancaSoapResponse response = new CobrancaSoapResponse();
            response.setSucesso(internalResponse.isSucesso());
            response.setMensagem(internalResponse.getMensagem());
            response.setAsaasId(internalResponse.getAsaasId());
            response.setStatus(internalResponse.getStatus());
            response.setInvoiceUrl(internalResponse.getInvoiceUrl());
            response.setBankSlipUrl(internalResponse.getBankSlipUrl());
            response.setPixQrCodeUrl(internalResponse.getPixQrCodeUrl());

            logger.info("[SOAP] Cobrança criada com sucesso. AsaasId: {}", response.getAsaasId());
            return response;

        } catch (Exception e) {
            logger.error("[SOAP] Erro ao criar cobrança: {}", e.getMessage(), e);
            return CobrancaSoapResponse.erro("Erro ao criar cobrança: " + e.getMessage());
        }
    }

    @Override
    public CobrancaSoapResponse cancelarCobranca(String asaasCobrancaId) {
        logger.info("[SOAP] Recebida requisição para cancelar cobrança. AsaasId: {}", asaasCobrancaId);

        try {
            boolean cancelada = asaasService.cancelarCobranca(asaasCobrancaId);

            CobrancaSoapResponse response = new CobrancaSoapResponse();
            response.setSucesso(cancelada);
            response.setAsaasId(asaasCobrancaId);

            if (cancelada) {
                response.setStatus("CANCELED");
                response.setMensagem("Cobrança cancelada com sucesso");
            } else {
                response.setMensagem("Não foi possível cancelar a cobrança");
            }

            return response;

        } catch (Exception e) {
            logger.error("[SOAP] Erro ao cancelar cobrança: {}", e.getMessage(), e);
            return CobrancaSoapResponse.erro("Erro ao cancelar cobrança: " + e.getMessage());
        }
    }

    @Override
    public CobrancaSoapResponse buscarCobranca(String asaasCobrancaId) {
        logger.info("[SOAP] Recebida requisição para buscar cobrança. AsaasId: {}", asaasCobrancaId);

        try {
            AsaasCobrancaResponse asaasResponse = asaasService.buscarCobranca(asaasCobrancaId);

            if (asaasResponse == null) {
                return CobrancaSoapResponse.erro("Cobrança não encontrada");
            }

            CobrancaSoapResponse response = new CobrancaSoapResponse();
            response.setSucesso(true);
            response.setMensagem("Cobrança encontrada");
            response.setAsaasId(asaasResponse.getId());
            response.setStatus(asaasResponse.getStatus());
            response.setInvoiceUrl(asaasResponse.getInvoiceUrl());
            response.setBankSlipUrl(asaasResponse.getBankSlipUrl());
            response.setPixQrCodeUrl(asaasResponse.getPixQrCodeUrl());

            return response;

        } catch (Exception e) {
            logger.error("[SOAP] Erro ao buscar cobrança: {}", e.getMessage(), e);
            return CobrancaSoapResponse.erro("Erro ao buscar cobrança: " + e.getMessage());
        }
    }

    /**
     * Converte requisição SOAP para requisição interna
     */
    private CriarCobrancaRequest convertToInternalRequest(CobrancaSoapRequest soapRequest) {
        CriarCobrancaRequest request = new CriarCobrancaRequest();
        request.setCobrancaId(soapRequest.getCobrancaId());
        request.setClienteId(soapRequest.getClienteId());
        request.setClienteAsaasId(soapRequest.getClienteAsaasId());
        request.setClienteNome(soapRequest.getClienteNome());
        request.setClienteCpfCnpj(soapRequest.getClienteCpfCnpj());
        request.setClienteEmail(soapRequest.getClienteEmail());
        request.setDescricao(soapRequest.getDescricao());
        request.setValor(soapRequest.getValor());
        request.setTipoPagamento(soapRequest.getTipoPagamento());

        // Converte data de String para LocalDate
        if (soapRequest.getDataVencimento() != null && !soapRequest.getDataVencimento().isEmpty()) {
            request.setDataVencimento(LocalDate.parse(soapRequest.getDataVencimento()));
        }

        return request;
    }
}
