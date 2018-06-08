package com.allen.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.WebApplicationInitializer;

@EnableEurekaServer
@SpringBootApplication
public class MicroserviceEurekaServerApplication extends SpringBootServletInitializer implements WebApplicationInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEurekaServerApplication.class, args);
	}
	
}
