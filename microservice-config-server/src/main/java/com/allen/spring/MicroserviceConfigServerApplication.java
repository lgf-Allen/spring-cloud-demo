package com.allen.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.WebApplicationInitializer;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class MicroserviceConfigServerApplication extends SpringBootServletInitializer implements WebApplicationInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceConfigServerApplication.class, args);
	}
	
}
