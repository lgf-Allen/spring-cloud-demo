/**
 * 
 */
package com.allen.spring.controller;

import java.util.List;

import com.netflix.appinfo.InstanceInfo;

/**
 * @author first
 * @create 2018-06-15 17:03
 *
 */
public interface DiscoveryInstanceController {

    public List<InstanceInfo> getInstance(String instanceId);
}
