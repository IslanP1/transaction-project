package com.adsifpb.charge_proxy.dto;

/**
 * DTO para atualizar status no Charge Manager
 */
public class AtualizarStatusRequest {

    private Long statusId;

    public AtualizarStatusRequest() {}

    public AtualizarStatusRequest(Long statusId) {
        this.statusId = statusId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
