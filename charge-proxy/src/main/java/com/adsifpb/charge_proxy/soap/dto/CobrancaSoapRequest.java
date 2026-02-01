package com.adsifpb.charge_proxy.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * DTO SOAP para requisição de criação de cobrança
 */
@XmlRootElement(name = "cobrancaRequest", namespace = "http://soap.charge_proxy.adsifpb.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CobrancaSoapRequest", namespace = "http://soap.charge_proxy.adsifpb.com/")
public class CobrancaSoapRequest {

    @XmlElement(required = true)
    private Long cobrancaId;

    @XmlElement(required = true)
    private Long clienteId;

    @XmlElement
    private String clienteAsaasId;

    @XmlElement(required = true)
    private String clienteNome;

    @XmlElement(required = true)
    private String clienteCpfCnpj;

    @XmlElement
    private String clienteEmail;

    @XmlElement(required = true)
    private String descricao;

    @XmlElement(required = true)
    private Double valor;

    @XmlElement(required = true)
    private String dataVencimento; // formato: yyyy-MM-dd

    @XmlElement(required = true)
    private String tipoPagamento; // PIX, BOLETO, CREDIT_CARD

    public CobrancaSoapRequest() {}

    // Getters e Setters
    public Long getCobrancaId() {
        return cobrancaId;
    }

    public void setCobrancaId(Long cobrancaId) {
        this.cobrancaId = cobrancaId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteAsaasId() {
        return clienteAsaasId;
    }

    public void setClienteAsaasId(String clienteAsaasId) {
        this.clienteAsaasId = clienteAsaasId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteCpfCnpj() {
        return clienteCpfCnpj;
    }

    public void setClienteCpfCnpj(String clienteCpfCnpj) {
        this.clienteCpfCnpj = clienteCpfCnpj;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
}
