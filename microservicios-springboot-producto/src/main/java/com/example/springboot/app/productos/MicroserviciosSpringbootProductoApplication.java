package com.example.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"pe.com.app.commons.entity"})
public class MicroserviciosSpringbootProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosSpringbootProductoApplication.class, args);
	}

}
