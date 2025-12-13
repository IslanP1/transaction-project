package com.adsifpb.charge_manager.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "charge-proxy", url = "${charge-proxy.url:http://charge-proxy:8081}")
public interface ChargeProxyClient {

    @GetMapping("/api/ping")
    String ping();
}
