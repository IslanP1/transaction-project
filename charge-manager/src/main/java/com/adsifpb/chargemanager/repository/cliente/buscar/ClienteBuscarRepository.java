package com.adsifpb.chargemanager.repository.cliente.buscar;

import com.adsifpb.chargemanager.model.cliente.Cliente;

public interface ClienteBuscarRepository {
    Cliente buscarCliente(Long clienteId);
}
