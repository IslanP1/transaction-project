package com.adsifpb.chargemanager.service.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import com.adsifpb.chargemanager.repository.cliente.listar.ClienteListarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteListarService {
    private final ClienteListarRepository clienteListarRepository;

    public ClienteListarService(ClienteListarRepository clienteListarRepository) {
        this.clienteListarRepository = clienteListarRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteListarRepository.listarClientes();
    }
}
