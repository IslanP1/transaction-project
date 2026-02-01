package com.adsifpb.chargemanager.service.email;

public interface EmailService {
    void enviarStatusCobrancaAlterado(
            Long cobrancaId,
            Long clienteId,
            Long statusId
    );
}