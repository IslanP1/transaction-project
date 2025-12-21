package com.adsifpb.chargemanager.controller.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.adsifpb.chargemanager.service.cliente.ClienteService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Cliente cliente) {
        // Implementação do endpoint para criar cliente
        service.salvarCliente(cliente);
    }
}
