/**
 * 
 */
package com.allen.spring.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author first
 *
 */
@RestController
public class HeolloController {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient client;
	
	@RequestMapping("/hello")
	public String index() {
		List<ServiceInstance> instances = client.getInstances("microservice-config-server");
		for(int i = 0;i<instances.size();i++) {
		logger.info("host:"+instances.get(i).getHost()+",service_id:"+instances.get(i).getServiceId());
		}
		return "Hello eureka";
	}
	
}
