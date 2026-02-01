package com.adsifpb.charge_proxy.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * DTO SOAP para resposta de operações de cobrança
 */
@XmlRootElement(name = "cobrancaResponse", namespace = "http://soap.charge_proxy.adsifpb.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CobrancaSoapResponse", namespace = "http://soap.charge_proxy.adsifpb.com/")
public class CobrancaSoapResponse {

    @XmlElement
    private String asaasId;

    @XmlElement
    private String status;

    @XmlElement
    private String invoiceUrl;

    @XmlElement
    private String bankSlipUrl;

    @XmlElement
    private String pixQrCodeUrl;

    @XmlElement
    private String mensagem;

    @XmlElement
    private boolean sucesso;

    public CobrancaSoapResponse() {}

    /**
     * Cria uma resposta de sucesso
     */
    public static CobrancaSoapResponse sucesso(String asaasId, String status,
                                                String invoiceUrl, String bankSlipUrl, String pixQrCodeUrl) {
        CobrancaSoapResponse response = new CobrancaSoapResponse();
        response.setAsaasId(asaasId);
        response.setStatus(status);
        response.setInvoiceUrl(invoiceUrl);
        response.setBankSlipUrl(bankSlipUrl);
        response.setPixQrCodeUrl(pixQrCodeUrl);
        response.setSucesso(true);
        response.setMensagem("Operação realizada com sucesso");
        return response;
    }

    /**
     * Cria uma resposta de erro
     */
    public static CobrancaSoapResponse erro(String mensagem) {
        CobrancaSoapResponse response = new CobrancaSoapResponse();
        response.setSucesso(false);
        response.setMensagem(mensagem);
        return response;
    }

    // Getters e Setters
    public String getAsaasId() {
        return asaasId;
    }

    public void setAsaasId(String asaasId) {
        this.asaasId = asaasId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public String getBankSlipUrl() {
        return bankSlipUrl;
    }

    public void setBankSlipUrl(String bankSlipUrl) {
        this.bankSlipUrl = bankSlipUrl;
    }

    public String getPixQrCodeUrl() {
        return pixQrCodeUrl;
    }

    public void setPixQrCodeUrl(String pixQrCodeUrl) {
        this.pixQrCodeUrl = pixQrCodeUrl;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
}
