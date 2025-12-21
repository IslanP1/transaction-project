package com.adsifpb.chargemanager.model.cobranca.event;

public class CobrancaStatusAlteradoEvent {
    private final Long cobrancaId;
    private final Long clienteId;
    private final Long statusId;

    public CobrancaStatusAlteradoEvent(Long cobrancaId, Long clienteId, Long statusId) {
        this.cobrancaId = cobrancaId;
        this.clienteId = clienteId;
        this.statusId = statusId;
    }

    public Long getCobrancaId() {
        return cobrancaId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Long getStatusId() {
        return statusId;
    }
}
