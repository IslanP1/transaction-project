package com.adsifpb.charge_proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProxyController {

    @GetMapping("/ping")
    public String ping() {
        return "Pong from Charge Proxy";
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "charge-proxy");
        status.put("version", "1.0.0");
        status.put("description", "Proxy para comunicação com ASAAS Payment Gateway");
        return status;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Charge Proxy");
        info.put("port", 8081);
        info.put("endpoints", Map.of(
            "criar_cobranca", "POST /api/cobranca",
            "cancelar_cobranca", "DELETE /api/cobranca/{asaasId}",
            "buscar_cobranca", "GET /api/cobranca/{asaasId}",
            "webhook_asaas", "POST /api/webhook/asaas"
        ));
        return info;
    }
}
