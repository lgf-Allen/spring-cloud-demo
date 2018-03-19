/**
 * 
 */
package com.allen.spring.controller;

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
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/hello")
	public String index() {
		ServiceInstance instance = client.getLocalServiceInstance();
		logger.info("host:"+instance.getHost()+",service_id:"+instance.getServiceId());
		return "Hello eureka";
	}
	
}
