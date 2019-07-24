package com.demo.titanic;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	/*
	 * Servlet Initializer that gets invoked in Spring startup pipeline for booting up web based services.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TitanicDemoApplication.class);
	}

}
