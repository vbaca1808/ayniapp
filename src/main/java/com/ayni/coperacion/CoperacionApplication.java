package com.ayni.coperacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class CoperacionApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(CoperacionApplication.class, args);
	}

}
