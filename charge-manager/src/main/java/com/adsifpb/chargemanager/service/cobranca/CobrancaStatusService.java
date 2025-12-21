package com.adsifpb.chargemanager.service.cobranca;

import com.adsifpb.chargemanager.repository.cobranca.CobrancaStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class CobrancaStatusService {
    private final CobrancaStatusRepository cobrancaStatusRepository;

    public CobrancaStatusService(CobrancaStatusRepository cobrancaStatusRepository) {
        this.cobrancaStatusRepository = cobrancaStatusRepository;
    }

    public void atualizarStatus(Long cobrancaId, Long clienteId, Long statusId) {
        cobrancaStatusRepository.atualizarStatus(cobrancaId, clienteId, statusId);
    }
}
