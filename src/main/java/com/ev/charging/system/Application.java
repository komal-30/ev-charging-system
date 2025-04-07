package com.ev.charging.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { 
	    "com.ev.charging.system.controller", // Scans controller package
	    "com.ev.charging.system.service"     // Add service package for scanning
	})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
