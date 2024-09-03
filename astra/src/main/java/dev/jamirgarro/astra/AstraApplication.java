package dev.jamirgarro.astra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "dev.jamirgarro.astra.model")
public class AstraApplication {


	public static void main(String[] args) {
		SpringApplication.run(AstraApplication.class, args);
	}



}
