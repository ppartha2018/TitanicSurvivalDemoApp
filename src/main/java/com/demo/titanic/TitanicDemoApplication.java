package com.demo.titanic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.demo.titanic.service.MainController;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class TitanicDemoApplication {

	/*
	 * Spring Application starter class.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TitanicDemoApplication.class, args);
	}

}
