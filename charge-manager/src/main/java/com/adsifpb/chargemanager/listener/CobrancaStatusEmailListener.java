package com.adsifpb.chargemanager.listener;

import com.adsifpb.chargemanager.model.cobranca.event.CobrancaStatusAlteradoEvent;
import com.adsifpb.chargemanager.service.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CobrancaStatusEmailListener {
    private final EmailService emailService;

    public CobrancaStatusEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void onStatusAlterado(CobrancaStatusAlteradoEvent event) {
        emailService.enviarStatusCobrancaAlterado(
                event.getCobrancaId(),
                event.getClienteId(),
                event.getStatusId()
        );
    }
}
