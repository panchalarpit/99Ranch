package com.tawasupermarket.purchasemicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PurchaseMicroserviceApplication {
	public static final Logger LOG = LoggerFactory.getLogger(PurchaseMicroserviceApplication.class);

	public static void main(String[] args) {
		LOG.error("Purchase microservice starting");
		SpringApplication.run(PurchaseMicroserviceApplication.class, args);
	}

}
