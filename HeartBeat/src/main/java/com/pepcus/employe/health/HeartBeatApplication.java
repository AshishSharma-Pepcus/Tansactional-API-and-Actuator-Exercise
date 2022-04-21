package com.pepcus.employe.health;

import com.pepcus.employe.health.healthService.HealthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HeartBeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeartBeatApplication.class, args);
		HealthService h = new HealthService();
		System.out.println(h.checkHealth());

	}

}
