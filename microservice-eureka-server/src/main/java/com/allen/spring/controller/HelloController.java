/**
 * 
 */
package com.allen.spring.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

/**
 * @author first
 *
 */
@RestController
public class HelloController {

	/** 日志记录类 */
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);

	/**
	 * 自动注入com.netflix.discovery.EurekaClient
	 */
	@Autowired
	private EurekaClient eurekaClient;// com.netflix.discovery.EurekaClient

	/**
	 * 根据region查询对应的Application信息
	 * 
	 * @param region,optional
	 *            param
	 * @return
	 */
	@RequestMapping(path = "/apps", method = RequestMethod.GET)
	public List<Application> getAllApps(@RequestParam(name = "region", required = false) String region,
			@RequestParam(name = "serviceId", required = false) String serviceId) {
		
		if (region != null) {
			logger.info("RequestParam region is : " + region);
		}
		// Determinate region is null or not null.
		Applications applications = region == null ? eurekaClient.getApplications()
				: eurekaClient.getApplicationsForARegion(region);
		if (applications == null) {
			return Collections.emptyList();
		}
		logger.info("Get for a region applications size is " + applications.size());
		
		List<Application> registered = new ArrayList<>();
		if(serviceId != null) {
			Application application= applications.getRegisteredApplications(serviceId);
			registered.add(application);
		}else {
			// Get all registered application.
			registered = applications.getRegisteredApplications();
		}
		
		return registered;
	}

}
