package com.tawasupermarket.customermicroservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerMicroserviceApplication {

	public static final Logger LOG = LoggerFactory.getLogger(CustomerMicroserviceApplication.class);

	public static void main(String[] args) {
		LOG.info("Customer service starting");
		SpringApplication.run(CustomerMicroserviceApplication.class, args);
	}

}
