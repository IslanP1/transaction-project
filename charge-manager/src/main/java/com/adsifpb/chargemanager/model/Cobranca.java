package com.adsifpb.chargemanager.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_charge")
public class Cobranca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String descricao;

    @Column(name = "amount", nullable = false)
    private Double quantia;

    @Column(name = "due_date", nullable = false)
    private LocalDate dataVencimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusCobranca statusCobranca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private MetodoPagamento metodoPagamento;

    public Cobranca() {
    }

    public Cobranca(
            String descricao,
            Double quantia,
            LocalDate dataVencimento,
            StatusCobranca status,
            MetodoPagamento metodoPagamento
    ) {
        this.descricao = descricao;
        this.quantia = quantia;
        this.dataVencimento = dataVencimento;
        this.statusCobranca = status;
        this.metodoPagamento = metodoPagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getQuantia() {
        return quantia;
    }

    public void setQuantia(Double quantia) {
        this.quantia = quantia;
    }

    public StatusCobranca getStatus() {
        return statusCobranca;
    }

    public void setStatus(StatusCobranca status) {
        this.statusCobranca = status;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}