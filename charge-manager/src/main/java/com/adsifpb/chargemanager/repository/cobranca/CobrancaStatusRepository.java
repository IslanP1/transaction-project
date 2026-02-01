package com.adsifpb.chargemanager.repository.cobranca;

public interface CobrancaStatusRepository  {
    void atualizarStatus(Long cobrancaId, Long clienteId, Long statusId);
}
