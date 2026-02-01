package com.adsifpb.charge_proxy.dto;

/**
 * DTO de resposta após criar cobrança no ASAAS
 */
public class CriarCobrancaResponse {

    private String asaasId;
    private String status;
    private String invoiceUrl;
    private String bankSlipUrl;
    private String pixQrCodeUrl;
    private String mensagem;
    private boolean sucesso;

    public CriarCobrancaResponse() {}

    public static CriarCobrancaResponse sucesso(String asaasId, String status,
                                                 String invoiceUrl, String bankSlipUrl, String pixQrCodeUrl) {
        CriarCobrancaResponse response = new CriarCobrancaResponse();
        response.setAsaasId(asaasId);
        response.setStatus(status);
        response.setInvoiceUrl(invoiceUrl);
        response.setBankSlipUrl(bankSlipUrl);
        response.setPixQrCodeUrl(pixQrCodeUrl);
        response.setSucesso(true);
        response.setMensagem("Cobrança criada com sucesso no ASAAS");
        return response;
    }

    public static CriarCobrancaResponse erro(String mensagem) {
        CriarCobrancaResponse response = new CriarCobrancaResponse();
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
