package dev.binz.reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"dev.binz.reference", "dev.binz.reference.adapter.out"})
public class ReferenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReferenceApplication.class, args);
	}

}
