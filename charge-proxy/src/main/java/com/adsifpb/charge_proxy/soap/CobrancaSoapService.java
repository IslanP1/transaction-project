package com.adsifpb.charge_proxy.soap;

import com.adsifpb.charge_proxy.soap.dto.CobrancaSoapRequest;
import com.adsifpb.charge_proxy.soap.dto.CobrancaSoapResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 * Interface SOAP para comunicação com o Charge Manager
 * Implementa o protocolo SOAP-RPC conforme especificação da arquitetura
 */
@WebService(name = "CobrancaService", targetNamespace = "http://soap.charge_proxy.adsifpb.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface CobrancaSoapService {

    /**
     * Cria uma nova cobrança no ASAAS
     */
    @WebMethod(operationName = "criarCobranca")
    @WebResult(name = "cobrancaResponse")
    CobrancaSoapResponse criarCobranca(
            @WebParam(name = "cobrancaRequest") CobrancaSoapRequest request
    );

    /**
     * Cancela uma cobrança existente no ASAAS
     */
    @WebMethod(operationName = "cancelarCobranca")
    @WebResult(name = "cancelamentoResponse")
    CobrancaSoapResponse cancelarCobranca(
            @WebParam(name = "asaasCobrancaId") String asaasCobrancaId
    );

    /**
     * Busca uma cobrança pelo ID do ASAAS
     */
    @WebMethod(operationName = "buscarCobranca")
    @WebResult(name = "cobrancaResponse")
    CobrancaSoapResponse buscarCobranca(
            @WebParam(name = "asaasCobrancaId") String asaasCobrancaId
    );
}
