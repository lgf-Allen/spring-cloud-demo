/**
 * 
 */
package com.allen.spring.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.allen.spring.bean.AgentMonitor;
import com.allen.spring.service.MvtmMonitor;

import net.sf.ehcache.Ehcache;

/**
 * @author first
 *
 */
@Service
public class MvtmMonitorImpl implements MvtmMonitor {

    private static Logger logger = LoggerFactory.getLogger(MvtmMonitorImpl.class);
    

    @Override
    public MvtmMonitor save(MvtmMonitor mvtm, AgentMonitor agent) {
        return null;
    }
    
    
   
}
