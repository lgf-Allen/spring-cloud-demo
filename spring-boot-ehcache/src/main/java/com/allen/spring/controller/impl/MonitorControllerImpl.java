/**
 * 
 */
package com.allen.spring.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.allen.spring.bean.AgentPO;
import com.allen.spring.service.MvtmMonitorService;

/**
 * @author first
 *
 */
@RestController
public class MonitorControllerImpl {
    
    @Autowired
    private  MvtmMonitorService mvtmMonitorService;
    
}
