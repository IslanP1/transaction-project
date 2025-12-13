package com.adsifpb.charge_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChargeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargeManagerApplication.class, args);
	}

}
