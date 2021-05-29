package com.example.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicroserviciosSpringbootProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosSpringbootProductoApplication.class, args);
	}

}
