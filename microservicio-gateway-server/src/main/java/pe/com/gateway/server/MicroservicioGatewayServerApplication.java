package pe.com.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicroservicioGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioGatewayServerApplication.class, args);
	}

}
