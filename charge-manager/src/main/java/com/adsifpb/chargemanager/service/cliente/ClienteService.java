package com.adsifpb.chargemanager.service.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adsifpb.chargemanager.repository.cliente.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public void salvarCliente(Cliente cliente) {
        clienteRepository.salvar(cliente);
    }
}
