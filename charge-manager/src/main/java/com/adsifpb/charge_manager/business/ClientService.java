package com.adsifpb.charge_manager.business;

import com.adsifpb.charge_manager.infra.Client;
import com.adsifpb.charge_manager.infra.ClientRepository;
import com.adsifpb.charge_manager.infra.ChargeProxyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Autowired
    private ChargeProxyClient proxyClient;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client save(Client client) {
        // Call proxy to notify or validate (mocking business logic)
        try {
            String proxyResponse = proxyClient.ping();
            System.out.println("Proxy response: " + proxyResponse);
        } catch (Exception e) {
            System.err.println("Failed to contact proxy: " + e.getMessage());
        }
        return repository.save(client);
    }
}
