package com.adsifpb.chargemanager.controller.cobranca;

import com.adsifpb.chargemanager.controller.cobranca.dto.AtualizarStatusCobrancaRequest;
import com.adsifpb.chargemanager.model.cobranca.Cobranca;
import com.adsifpb.chargemanager.service.cobranca.CobrancaEfetuarService;
import com.adsifpb.chargemanager.service.cobranca.CobrancaStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cobranca")
public class CobrancaController {
    private final CobrancaEfetuarService efetuarService;
    private final CobrancaStatusService statusService;

    public CobrancaController(CobrancaEfetuarService efetuarService, CobrancaStatusService statusService) {
        this.efetuarService = efetuarService;
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<Cobranca> criar(@RequestBody Cobranca cobranca) {
        Cobranca cobrancaCriada = efetuarService.efetuar(cobranca);
        return ResponseEntity.ok(cobrancaCriada);
    }

    @PatchMapping("/{cobrancaId}")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long cobrancaId,
            @RequestParam Long clienteId,
            @RequestBody AtualizarStatusCobrancaRequest request
    ) {
        statusService.atualizarStatus(
                cobrancaId,
                clienteId,
                request.getStatusId()
        );

        return ResponseEntity.noContent().build();
    }
}

