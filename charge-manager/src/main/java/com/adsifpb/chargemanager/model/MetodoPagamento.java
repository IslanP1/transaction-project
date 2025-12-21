package com.adsifpb.chargemanager.model;

import jakarta.persistence.*;

@Entity
@Table(name= "tb_charge_payment_method")
public class MetodoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code", nullable = false)
    private String code;

    @Column(name="description", nullable = false)
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    //TODO [Reverse Engineering] generate columns from DB
}