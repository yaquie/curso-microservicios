package com.example.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@RibbonClient(name = "servicio-productos")
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class MicroserviciosSpringbootItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosSpringbootItemApplication.class, args);
	}

}
