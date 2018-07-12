/**
 * 
 */
package com.allen.spring.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.allen.spring.bean.AgentPO;
import com.allen.spring.bean.Device;
import com.allen.spring.service.MvtmMonitorService;

/**
 * @author first
 *
 */
@Service
public class MvtmMonitorServiceImpl implements MvtmMonitorService {

    private static Logger logger = LoggerFactory.getLogger(MvtmMonitorServiceImpl.class);
  
    @Resource
    private CacheManager manager;
    
    @Override
    public List<Device> saveMvtm(Device device) {
        return null;
    }

    @CachePut(value = "mvtmEhcache", key = "#agent.staffId")
    @Override
    public AgentPO saveVta(AgentPO agent) {
        System.out.println(agent.getStaffName());
        return agent;
    }

    
    @Override
    public AgentPO get(String staffId) {
       Cache cache =  manager.getCache("mvtmEhcache");
       AgentPO agent = cache.get(staffId, AgentPO.class);
        return agent;
    }
    
    
   
}
