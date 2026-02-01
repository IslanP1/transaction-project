package com.adsifpb.charge_proxy.dto;

import java.time.LocalDate;

/**
 * DTO para receber requisição de criação de cobrança do Charge Manager
 */
public class CriarCobrancaRequest {

    private Long cobrancaId;
    private Long clienteId;
    private String clienteAsaasId; // ID do cliente no ASAAS
    private String clienteNome;
    private String clienteCpfCnpj;
    private String clienteEmail;
    private String descricao;
    private Double valor;
    private LocalDate dataVencimento;
    private String tipoPagamento; // PIX, BOLETO, CREDIT_CARD

    public CriarCobrancaRequest() {}

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

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
}
