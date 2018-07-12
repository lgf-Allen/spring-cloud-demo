/**
 * 
 */
package com.allen.spring.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allen.spring.bean.ActionEvent;
import com.allen.spring.bean.MonitorInstance;
import com.allen.spring.bean.MvtmMonitor;
import com.allen.spring.service.MvtmMonitorService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

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
    public MonitorInstance update(ActionEvent event) {
        Cache cache = manager.getCache("mvtmEhcache");
        if("vtm".equals(event.getOwner())) {
            MvtmMonitor mvtm = new MvtmMonitor();
            mvtm.setBranchId(event.getOwnerId());
            mvtm.setBranchName(event.getOwnerName());
            mvtm.setTransactionId(event.getTransactionId());
            String action = event.getAction();
            
        }
        if("agent".equals(event.getOwner())) {
            
        }
        return null;
    }

    
}
