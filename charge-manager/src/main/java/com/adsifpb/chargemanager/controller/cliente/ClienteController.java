package com.adsifpb.chargemanager.controller.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import com.adsifpb.chargemanager.service.cliente.ClienteBuscarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.adsifpb.chargemanager.service.cliente.ClienteSalvarService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteSalvarService salvar;
    private final ClienteBuscarService buscar;

    public ClienteController(ClienteSalvarService salvar, ClienteBuscarService buscar) {
        this.salvar = salvar;
        this.buscar = buscar;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Cliente cliente) {
        // Implementação do endpoint para criar cliente
        salvar.salvarCliente(cliente);
    }
    
    @GetMapping("/{clienteId}")
    public Cliente getCliente(@PathVariable Long clienteId) {
        // Implementação do endpoint para buscar cliente
        return buscar.buscarCliente(clienteId);
    }
}
