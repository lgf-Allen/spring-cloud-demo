/**
 * 
 */
package com.allen.spring.service;

import java.util.List;

import com.allen.spring.bean.AgentPO;
import com.allen.spring.bean.Device;
import com.allen.spring.bean.MvtmMonitor;

/**
 * @author first
 *
 */
public interface MvtmMonitorService {

    List<Device> saveMvtm(Device device);
    
    AgentPO saveVta(AgentPO agent);
    
    AgentPO get(String staffId);
}
