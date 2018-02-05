/**
 * 
 */
package com.allen.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author first
 *
 */
@RefreshScope
@RestController
public class NameController {

	@Value("${name}")
	private String name;

	@Autowired
	private Environment env;
	
	@RequestMapping("/hello")
	public String hello() {
		return name;
	}
	
	@RequestMapping("/lisi")
	public String test() {
		return env.getProperty("name", "zhangsan");
	}
	
}
