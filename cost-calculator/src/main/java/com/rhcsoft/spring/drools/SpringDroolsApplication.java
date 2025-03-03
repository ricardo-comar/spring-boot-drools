package com.rhcsoft.spring.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rhcsoft.spring.drools")
public class SpringDroolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDroolsApplication.class, args);
	}

}
