package com.adsifpb.charge_proxy.dto.asaas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para criar cliente no ASAAS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsaasClienteRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("cpfCnpj")
    private String cpfCnpj;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("externalReference")
    private String externalReference;

    public AsaasClienteRequest() {}

    public AsaasClienteRequest(String name, String cpfCnpj, String email, String phone, String externalReference) {
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.phone = phone;
        this.externalReference = externalReference;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }
}
