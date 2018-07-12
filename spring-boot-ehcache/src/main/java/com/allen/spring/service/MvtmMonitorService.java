/**
 * 
 */
package com.allen.spring.service;

import com.allen.spring.bean.ActionEvent;
import com.allen.spring.bean.MonitorInstance;

/**
 * @author first
 *
 */
public interface MvtmMonitorService {
    
    /**
     * receive action event from front side ,then calcuate new status and  update memory object
     * @param event
     */
    MonitorInstance update(ActionEvent event);

}
