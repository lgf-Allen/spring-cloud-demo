/**
 * 
 */
package com.allen.spring.controller.impl;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allen.spring.bean.AgentMonitor;
import com.allen.spring.bean.MvtmMonitor;
import com.allen.spring.service.MvtmMonitorService;

/**
 * @author first
 *
 */
@RestController
public class MonitorControllerImpl {
    
    @Autowired
    private  MvtmMonitorService mvtmMonitorService;
    
    @RequestMapping(path="/MvtmMonitor" , method = RequestMethod.POST)
    public MvtmMonitor addMvtm(@RequestBody MvtmMonitor mvtm) {
        AgentMonitor agent = new AgentMonitor();
        MvtmMonitor response = mvtmMonitorService.save(mvtm, agent);
        return response;
    }
    
    @RequestMapping(path="/mvtm",method=RequestMethod.GET)
    public MvtmMonitor getMvtm() {
        MvtmMonitor mvtm =  mvtmMonitorService.get();
        return mvtm;
    }
    
}
