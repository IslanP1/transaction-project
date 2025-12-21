package com.adsifpb.chargemanager.service.cobranca;

import com.adsifpb.chargemanager.model.cobranca.event.CobrancaStatusAlteradoEvent;
import com.adsifpb.chargemanager.repository.cobranca.CobrancaStatusRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CobrancaStatusService {
    private final CobrancaStatusRepository repository;
    private final ApplicationEventPublisher publisher;

    public CobrancaStatusService(CobrancaStatusRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void atualizarStatus(Long cobrancaId, Long clienteId, Long statusId) {
        repository.atualizarStatus(cobrancaId, clienteId, statusId);

        // Publica o evento de alteração de status
        publisher.publishEvent(
                new CobrancaStatusAlteradoEvent(
                        cobrancaId,
                        clienteId,
                        statusId
                )
        );
    }
}
