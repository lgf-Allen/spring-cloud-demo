/**
 * 
 */
package com.allen.spring.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.allen.spring.bean.AgentMonitor;
import com.allen.spring.bean.MvtmMonitor;
import com.allen.spring.service.MvtmMonitorService;

import net.sf.ehcache.Ehcache;

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
    public MvtmMonitor save(MvtmMonitor mvtm, AgentMonitor agent) {
        Cache cache = manager.getCache("mvtmEhcache");
        cache.put("mvtm_key", mvtm);
        return mvtm;
    }

    @Override
    public MvtmMonitor get() {
        Cache cache = manager.getCache("mvtmEhcache");
        MvtmMonitor value = cache.get("mvtm_key", MvtmMonitor.class);
        return value;
    }
    
    
   
}
