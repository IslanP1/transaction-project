package com.adsifpb.charge_proxy.dto.asaas;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para criar cobrança no ASAAS
 * Documentação: https://docs.asaas.com/reference/criar-nova-cobranca
 */
public class AsaasCobrancaRequest {

    @JsonProperty("customer")
    private String customerId;

    @JsonProperty("billingType")
    private String billingType; // BOLETO, CREDIT_CARD, PIX

    @JsonProperty("value")
    private Double value;

    @JsonProperty("dueDate")
    private String dueDate; // formato: yyyy-MM-dd

    @JsonProperty("description")
    private String description;

    @JsonProperty("externalReference")
    private String externalReference; // ID da cobrança no nosso sistema

    public AsaasCobrancaRequest() {}

    public AsaasCobrancaRequest(String customerId, String billingType, Double value,
                                 String dueDate, String description, String externalReference) {
        this.customerId = customerId;
        this.billingType = billingType;
        this.value = value;
        this.dueDate = dueDate;
        this.description = description;
        this.externalReference = externalReference;
    }

    // Getters e Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }
}
