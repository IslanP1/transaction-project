package com.adsifpb.chargemanager.service.cliente;

import com.adsifpb.chargemanager.model.cliente.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adsifpb.chargemanager.repository.cliente.salvar.ClienteSalvarRepository;

@Service
public class ClienteSalvarService {
    private final ClienteSalvarRepository clienteSalvarRepository;

    public ClienteSalvarService(ClienteSalvarRepository clienteSalvarRepository) {
        this.clienteSalvarRepository = clienteSalvarRepository;
    }

    @Transactional
    public void salvarCliente(Cliente cliente) {
        clienteSalvarRepository.salvar(cliente);
    }
}
