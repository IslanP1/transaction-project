package com.adsifpb.charge_proxy.dto.asaas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de resposta do ASAAS ao criar/consultar cobrança
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsaasCobrancaResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("customer")
    private String customerId;

    @JsonProperty("billingType")
    private String billingType;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("dueDate")
    private String dueDate;

    @JsonProperty("status")
    private String status; // PENDING, RECEIVED, CONFIRMED, OVERDUE, REFUNDED, etc.

    @JsonProperty("description")
    private String description;

    @JsonProperty("externalReference")
    private String externalReference;

    @JsonProperty("invoiceUrl")
    private String invoiceUrl;

    @JsonProperty("bankSlipUrl")
    private String bankSlipUrl;

    @JsonProperty("pixQrCodeUrl")
    private String pixQrCodeUrl;

    @JsonProperty("deleted")
    private Boolean deleted;

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
