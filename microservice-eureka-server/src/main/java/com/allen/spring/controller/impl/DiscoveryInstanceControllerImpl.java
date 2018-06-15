/**
 * 
 */
package com.allen.spring.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.allen.spring.controller.DiscoveryInstanceController;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

/**
 * @author first
 *
 */
@RestController
public class DiscoveryInstanceControllerImpl implements DiscoveryInstanceController {

    /** 日志记录类 */
    private static Logger logger = LoggerFactory.getLogger(DiscoveryInstanceControllerImpl.class);

    /**
     * 自动注入com.netflix.discovery.EurekaClient
     */
    @Autowired
    private EurekaClient eurekaClient;// com.netflix.discovery.EurekaClient

    /**
     * 根据instanceId查找对应的服务器实例
     * 
     * @param instanceId,optional
     *            param
     * @return List<InstanceInfo>
     */
    @RequestMapping(path = "/apps", method = RequestMethod.GET)
    public List<InstanceInfo> getInstance(@RequestParam(name = "instanceId", required = false) String instanceId) {

        // Get applications."EUREKA-CLIENT"代表中转服务器名称
        Application application = eurekaClient.getApplication("EUREKA-CLIENT");
        if (application == null) {// 没有可用的中转服务器
            logger.warn("Don't find transfer server.");
            return Collections.emptyList();
        }
        return findInstancesByInstanceId(application, instanceId);
    }

    /**
     * 根据instanceId在Application中查找可用InstanceInfo
     * 
     * @param application
     * @param instanceId,if
     *            instanceId is null return all InstanceInfo; but instanceId is not
     *            null return only one InstanceInfo or empty List.
     * @return List<InstanceInfo>
     */
    private List<InstanceInfo> findInstancesByInstanceId(Application application, String instanceId) {
        List<InstanceInfo> instances = new ArrayList<InstanceInfo>();
        if (instanceId != null) {
            InstanceInfo instanceInfo = application.getByInstanceId(instanceId);
            if (instanceInfo != null) {// 根据instanceId查询到一个可用的服务
                instances.add(instanceInfo);
                logger.info("Find a transfer server by instanceId,the ip is {}", instanceInfo.getIPAddr());
            }
        } else {// 查询所有可用的中转服务器
            instances = application.getInstances();
            logger.info("Find all transfer server.");
        }
        return instances;
    }
}
