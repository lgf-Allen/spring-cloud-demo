/**
 * 
 */
package com.allen.spring.service;

import com.allen.spring.bean.AgentMonitor;
import com.allen.spring.bean.MvtmMonitor;

/**
 * @author first
 *
 */
public interface MvtmMonitorService {

    MvtmMonitor save(MvtmMonitor mvtm, AgentMonitor agent);
    
    MvtmMonitor get();
}
