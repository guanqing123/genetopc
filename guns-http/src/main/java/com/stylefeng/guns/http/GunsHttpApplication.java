package com.stylefeng.guns.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
public class GunsHttpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GunsHttpApplication.class, args);
	}

}
