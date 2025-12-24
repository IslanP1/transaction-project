package com.adsifpb.chargemanager.controller.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import com.adsifpb.chargemanager.service.cliente.ClienteBuscarService;
import com.adsifpb.chargemanager.service.cliente.ClienteListarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.adsifpb.chargemanager.service.cliente.ClienteSalvarService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteSalvarService salvar;
    private final ClienteBuscarService buscar;
    private final ClienteListarService listar;

    public ClienteController(ClienteSalvarService salvar, ClienteBuscarService buscar, ClienteListarService listar) {
        this.salvar = salvar;
        this.buscar = buscar;
        this.listar = listar;
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

    @GetMapping
    public List<Cliente> listClientes() {
        return listar.listarClientes();
    }

}
