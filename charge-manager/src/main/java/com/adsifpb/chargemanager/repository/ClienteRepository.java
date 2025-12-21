package com.adsifpb.chargemanager.repository;

import com.adsifpb.chargemanager.model.Cliente;

// Se atentar aos principios SOLID -> Single Responsibility Principle
// Se atentar aos principios de Clean Architecture -> Repositorio deve depender de Model e nao o contrario
// Evitar logica de negocio dentro do repositorio
// Se atentar ao Singleton Pattern -> Repositorio deve ser singleton
public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
}
