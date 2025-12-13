package com.adsifpb.charge_manager.api;

import com.adsifpb.charge_manager.business.ClientService;
import com.adsifpb.charge_manager.infra.ChargeProxyClient;
import com.adsifpb.charge_manager.infra.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @Autowired
    private ChargeProxyClient proxyClient;

    @GetMapping
    public List<Client> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Client create(@RequestBody Client client) {
        return service.save(client);
    }

    @GetMapping("/proxy-test")
    public String testProxy() {
        return "Manager received: " + proxyClient.ping();
    }
}
