package com.example.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@RibbonClient(name = "servicio-productos")
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class MicroserviciosSpringbootItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosSpringbootItemApplication.class, args);
	}

}
