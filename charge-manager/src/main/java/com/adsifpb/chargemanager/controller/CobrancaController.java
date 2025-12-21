package com.adsifpb.chargemanager.controller;

import com.adsifpb.chargemanager.model.Cobranca;
import com.adsifpb.chargemanager.service.CobrancaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cobranca")
public class CobrancaController {
    private final CobrancaService service;

    public CobrancaController(CobrancaService cobrancaService) {
        this.service = cobrancaService;
    }

    @PostMapping
    public void create(@RequestBody Cobranca cobranca) {
        service.efetuar(cobranca);
    }

}
