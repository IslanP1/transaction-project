package com.adsifpb.charge_proxy.dto.asaas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para receber webhooks do ASAAS
 * Documentação: https://docs.asaas.com/reference/webhooks
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsaasWebhookEvent {

    @JsonProperty("event")
    private String event;

    @JsonProperty("payment")
    private AsaasPaymentWebhook payment;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public AsaasPaymentWebhook getPayment() {
        return payment;
    }

    public void setPayment(AsaasPaymentWebhook payment) {
        this.payment = payment;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AsaasPaymentWebhook {

        @JsonProperty("id")
        private String id;

        @JsonProperty("customer")
        private String customerId;

        @JsonProperty("value")
        private Double value;

        @JsonProperty("status")
        private String status;

        @JsonProperty("billingType")
        private String billingType;

        @JsonProperty("externalReference")
        private String externalReference;

        @JsonProperty("description")
        private String description;

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

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBillingType() {
            return billingType;
        }

        public void setBillingType(String billingType) {
            this.billingType = billingType;
        }

        public String getExternalReference() {
            return externalReference;
        }

        public void setExternalReference(String externalReference) {
            this.externalReference = externalReference;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
