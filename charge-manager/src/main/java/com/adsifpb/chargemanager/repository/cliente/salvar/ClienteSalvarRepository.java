package com.adsifpb.chargemanager.repository.cliente.salvar;

import com.adsifpb.chargemanager.model.cliente.Cliente;

// Se atentar aos principios SOLID -> Single Responsibility Principle
// Se atentar aos principios de Clean Architecture -> Repositorio deve depender de Model e nao o contrario
// Evitar logica de negocio dentro do repositorio
// Se atentar ao Singleton Pattern -> Repositorio deve ser singleton
public interface ClienteSalvarRepository {
    Cliente salvar(Cliente cliente);
}
