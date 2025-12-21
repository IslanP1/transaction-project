package com.adsifpb.chargemanager.service.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import com.adsifpb.chargemanager.repository.cliente.ClienteBuscarRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteBuscarService {
    private final ClienteBuscarRepository clienteBuscarRepository;

    public ClienteBuscarService(ClienteBuscarRepository clienteBuscarRepository) {
        this.clienteBuscarRepository = clienteBuscarRepository;
    }

    public Cliente buscarCliente(Long clienteId) {
        return clienteBuscarRepository.buscarCliente(clienteId);
    }
}
