package com.adsifpb.chargemanager.model.cobranca;

import java.time.LocalDate;

public class Cobranca {
    private String descricao;
    private Double quantia;
    private LocalDate dataVencimento;
    private Integer statusId;
    private Integer metodoPagamentoId;
    private Integer clienteId;
    
    public Cobranca() {}

    public Cobranca(String descricao, Double quantia, LocalDate dataVencimento,
                    Integer statusId, Integer metodoPagamentoId) {
        this.descricao = descricao;
        this.quantia = quantia;
        this.dataVencimento = dataVencimento;
        this.statusId = statusId;
        this.metodoPagamentoId = metodoPagamentoId;
        this.clienteId = clienteId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getQuantia() {
        return quantia;
    }

    public void setQuantia(Double quantia) {
        this.quantia = quantia;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getMetodoPagamentoId() {
        return metodoPagamentoId;
    }

    public void setMetodoPagamentoId(Integer metodoPagamentoId) {
        this.metodoPagamentoId = metodoPagamentoId;
    }
}
