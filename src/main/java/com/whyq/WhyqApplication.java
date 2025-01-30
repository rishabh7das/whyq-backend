package com.whyq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {})
public class WhyqApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhyqApplication.class, args);
	}

}
