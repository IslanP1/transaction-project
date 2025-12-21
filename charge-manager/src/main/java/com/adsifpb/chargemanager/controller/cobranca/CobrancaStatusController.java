package com.adsifpb.chargemanager.controller.cobranca;

import com.adsifpb.chargemanager.controller.cobranca.dto.AtualizarStatusCobrancaRequest;
import com.adsifpb.chargemanager.service.cobranca.CobrancaStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cobranca")
public class CobrancaStatusController {
    private final CobrancaStatusService service;

    public CobrancaStatusController(CobrancaStatusService service) {
        this.service = service;
    }

    @PatchMapping("/status/{cobrancaId}")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long cobrancaId,
            @RequestParam Long clienteId,
            @RequestBody AtualizarStatusCobrancaRequest request
    ) {
        service.atualizarStatus(
                cobrancaId,
                clienteId,
                request.getStatusId()
        );

        return ResponseEntity.noContent().build();
    }

}
