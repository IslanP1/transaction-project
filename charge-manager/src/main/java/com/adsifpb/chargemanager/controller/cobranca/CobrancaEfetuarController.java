package com.adsifpb.chargemanager.controller.cobranca;

import com.adsifpb.chargemanager.model.cobranca.Cobranca;
import com.adsifpb.chargemanager.service.cobranca.CobrancaEfetuarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cobranca")
public class CobrancaEfetuarController {
    private final CobrancaEfetuarService service;

    public CobrancaEfetuarController(CobrancaEfetuarService cobrancaEfetuarService) {
        this.service = cobrancaEfetuarService;
    }

    @PostMapping
    public void create(@RequestBody Cobranca cobranca) {
        service.efetuar(cobranca);
    }

}
