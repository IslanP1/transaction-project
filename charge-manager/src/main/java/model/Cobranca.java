package model;

public class Cobranca {
    private String id;
    private String descricao;
    private double quantia;
    private String dataVencimento;
    private Integer status; // 0 - Pendente, 1 - Registrada, 2 - Paga, 3 - Cancelada
    private Integer metodoPagamento; // 0 - Pix, 1 - Boleto, 2 - Cartão de Crédito

    public Cobranca() {
    }

    public Cobranca(String id, String descricao, double quantia, String dataVencimento, Integer status, Integer metodoPagamento) {
        this.id = id;
        this.descricao = descricao;
        this.quantia = quantia;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.metodoPagamento = metodoPagamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQuantia() {
        return quantia;
    }

    public void setQuantia(double quantia) {
        this.quantia = quantia;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(Integer metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
