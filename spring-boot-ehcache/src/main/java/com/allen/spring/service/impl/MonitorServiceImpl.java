/**
 * 
 */
package com.allen.spring.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.allen.spring.bean.ActionEvent;
import com.allen.spring.bean.AgentMonitor;
import com.allen.spring.bean.MonitorInstance;
import com.allen.spring.bean.MvtmMonitor;
import com.allen.spring.constant.ActionConstant;
import com.allen.spring.service.MonitorService;

/**
 * @author first
 *
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private static Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);

    private static final String CACHEKEY = "cacheKey";
    @Resource
    private CacheManager manager;

    @Override
    public MonitorInstance collectData(ActionEvent event) {
        Cache cache = manager.getCache("mvtmEhcache");
        MonitorInstance instance = null;
        String action = event.getAction();
        switch (action) {

        case ActionConstant.IDCHECK_S:
            instance = scanStart(cache, event);
            break;
        case ActionConstant.IDCHECK_E:
            instance = scanEnd(cache, event);
            break;
        case ActionConstant.FORM_S:
            instance = fillFormStart(cache, event);
            break;
        case ActionConstant.VC_APPLY_S:
            instance = connectVideoStart(cache, event);
            break;
        case ActionConstant.VC_APPLY_E:
            instance = connectVideoEnd(cache, event);
            break;
        case ActionConstant.VC_CONN:
            instance = connectVideoSuccess(cache, event);
            break;
        case ActionConstant.VC_RETRY:
            instance = connectVideoRetry(cache, event);
            break;
        case ActionConstant.CANCEL:
            instance = cancelRetry(cache, event);
            break;
        case ActionConstant.VC_DISCONN:
            break;

        case ActionConstant.SIGNON:
            instance = signOn(cache, event);
            break;
        case ActionConstant.SIGNOFF:
            instance = signOff(cache, event);
            break;
        case ActionConstant.ENDCALL:
            instance = endCall(cache, event);
            break;
        case ActionConstant.EXIT:
            instance = exit(cache, event);
            break;
        default :
            instance = notReady(cache, event);
            break;
        }
        return instance;
    }

    /**
     * Action is IDCHECK_S.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance scanStart(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = null;
        if (instance != null && instance.getMvtms() != null) {// Cache has List<MvtmMonitor>
            mvtms = instance.getMvtms();
            boolean flag = false;
            for (MvtmMonitor mvtmMonitor : mvtms) {
                while (event.getOwnerId().equals(mvtmMonitor.getBranchId())) {// 缓存中存在当前的branchId信息
                    mvtmMonitor.setId(event.getId());
                    mvtmMonitor.setAction(event.getAction());
                    flag = true;
                }
            }
            if (!flag) {
                MvtmMonitor mvtm = getNewInstance(event);
                mvtms.add(mvtm);
            }
            instance.setMvtms(mvtms);
            instance = calculateData(instance);
        } else if (instance == null) { // No cache
            instance = new MonitorInstance();
            mvtms = new ArrayList<MvtmMonitor>();
            MvtmMonitor mvtm = getNewInstance(event);
            mvtms.add(mvtm);
            instance.setMvtms(mvtms);
        } else {// instance != null && instance.getMvtms = null
            mvtms = new ArrayList<MvtmMonitor>();
            MvtmMonitor mvtm = getNewInstance(event);
            mvtms.add(mvtm);
            instance.setMvtms(mvtms);
        }
        // Send instance to webSocket
        cache.put(CACHEKEY, instance);
        return instance;
    }

    private MvtmMonitor getNewInstance(ActionEvent event) {
        MvtmMonitor mvtm = new MvtmMonitor();
        mvtm.setId(event.getId());
        mvtm.setBranchId(event.getOwnerId());
        mvtm.setBranchName(event.getOwnerName());
        mvtm.setAction(event.getAction());
        return mvtm;
    }

    /**
     * Action is IDCHECK_E.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance scanEnd(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            // 使用id来确定同一单的缓存,因为branchId和 branchName会出现重复
            if (event.getId().equals(mvtmMonitor.getId())) {
                mvtmMonitor.setAction(event.getAction());
                mvtmMonitor.setTransactionId(event.getTransactionId());
                mvtmMonitor.setScanTime(new Date());// 设置扫描身份证时间
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to webSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is FORM_S.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance fillFormStart(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                mvtmMonitor.setLastUpdateTime(new Date());
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to webSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is VC_APPLY_S.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance connectVideoStart(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        Date now = new Date();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                mvtmMonitor.setCallTimes(event.getCallTimes());
                // 获取form表单填写时间
                long fillDurationTime = now.getTime() - mvtmMonitor.getLastUpdateTime().getTime();
                mvtmMonitor.setFillDurationTime(fillDurationTime);// 设置form表单填写时间
                // TODO Save fillDurationTime to db.
                mvtmMonitor.setLastUpdateTime(now);// 设置视频连接开始时间
            }
        }
        int waitIncomingNumber = instance.getWaitIncomingNumber();
        instance.setWaitIncomingNumber(waitIncomingNumber + 1);
        int totalCallingNumber = instance.getTotalCallingNumber();
        instance.setTotalCallingNumber(totalCallingNumber + 1);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to webSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    private MonitorInstance connectVideoEnd(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        Date now = new Date();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                // 设置拨打次数
                mvtmMonitor.setCallTimes(event.getCallTimes());
                // 设置超过30s未能接入个数
                mvtmMonitor.setRetryTimes(event.getRetryTimes());
                long waitCallTime = now.getTime() - mvtmMonitor.getLastUpdateTime().getTime();
                // 设置等候拨入时长
                mvtmMonitor.setWaitCallTime(waitCallTime);
            }
        }
        int waitIncomingNumber = instance.getWaitIncomingNumber();
        // 等待接入VTA个数
        instance.setWaitIncomingNumber(waitIncomingNumber - 1);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is VC_CONN.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance connectVideoSuccess(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();

        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                long currentWaitTime = System.currentTimeMillis() - mvtmMonitor.getLastUpdateTime().getTime();
                long waitCallSuccessTime = currentWaitTime + mvtmMonitor.getWaitCallTime();// 成功接通时间总计
                // TODO Save waitCallSuccessTime to db.
                mvtmMonitor.setWaitCallSuccessTime(waitCallSuccessTime);
            }
        }
        int waitIncomingNumber = instance.getWaitIncomingNumber();
        // 等待接入VTA个数
        instance.setWaitIncomingNumber(waitIncomingNumber - 1);
        int successCallingNumber = instance.getSucessCallingNumber();
        // 成功接入总数
        instance.setSucessCallingNumber(successCallingNumber + 1);
        int totalCallingNumber = instance.getTotalCallingNumber();
        double ABN = (totalCallingNumber - (successCallingNumber + 1)) / totalCallingNumber * 100;
        instance.setABN(ABN);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is VC_RETRY.
     * 
     * @param cache
     * @param event
     * @returns
     */
    private MonitorInstance connectVideoRetry(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                mvtmMonitor.setCallTimes(event.getCallTimes());
                mvtmMonitor.setRetryTimes(event.getRetryTimes());
                // 设置retry开始时间
                mvtmMonitor.setLastUpdateTime(new Date());
            }
        }
        int totalCallingNumber = instance.getTotalCallingNumber();
        instance.setTotalCallingNumber(totalCallingNumber + 1);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * 重试框弹出，点击取消，触发事件
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance cancelRetry(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is SIGNON.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance signOn(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<AgentMonitor> agents = null;
        Date now = new Date();
        if (instance != null && instance.getAgents() != null) {// Cache has List<AgentMonitor>
            agents = instance.getAgents();
            boolean flag = false;
            for (AgentMonitor agentMonitor : agents) {
                if (event.getOwnerId().equals(agentMonitor.getStaffId())) {
                    agentMonitor.setSignOnTime(now);
                    // Set ready status times
                    Map<String, Integer> map = agentMonitor.getStatusTimes();
                    int times = map.get(ActionConstant.READY);
                    map.put(ActionConstant.READY, times + 1);
                    agentMonitor.setStatusTimes(map);
                    flag = true;
                }
            }
            if (!flag) {// cache do not hava the AgentMonitor
                AgentMonitor agent = getAgentNewInstance(event, now);
                agents.add(agent);
            }
        } else if (instance == null) {
            instance = new MonitorInstance();
            agents = new ArrayList<AgentMonitor>();
            AgentMonitor agent = getAgentNewInstance(event, now);
            agents.add(agent);
        } else {
            agents = new ArrayList<AgentMonitor>();
            AgentMonitor agent = getAgentNewInstance(event, now);
            agents.add(agent);
        }
        instance.setAgents(agents);
        int readyNumber = instance.getReadyNumber();
        instance.setReadyNumber(readyNumber + 1);
        if (instance.getMvtms() != null) {// Caceh has List<MvtmMonitor>
            instance = calculateData(instance);
        }
        // TODO Save sign on time to db.
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);
        return instance;
    }

    private AgentMonitor getAgentNewInstance(ActionEvent event, Date now) {
        AgentMonitor agent = new AgentMonitor();
        agent.setStaffId(event.getOwnerId());
        agent.setStaffName(event.getOwnerName());
        agent.setSignOnTime(now);
        agent.setWorkingStatus(ActionConstant.READY);// Ready status
        agent.setLastUpdateTime(new Date());// Ready status start time
        Map<String, Integer> map = new HashMap<>();
        map.put(ActionConstant.READY, 1);
        agent.setStatusTimes(map);
        return agent;
    }

    /**
     * Action is SIGNOFF.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance signOff(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<AgentMonitor> agents = instance.getAgents();
        for (AgentMonitor agentMonitor : agents) {
            if (event.getOwnerId().equals(agentMonitor.getStaffId())) {
                Date now = new Date();
                agentMonitor.setSignOffTime(now);// 设置sign off时间
                long signDuration = now.getTime() - agentMonitor.getSignOnTime().getTime();
                agentMonitor.setSignDuration(signDuration);// 计算sign on时间
                String workingStatus = agentMonitor.getWorkingStatus();
                long workingDuration = now.getTime() - agentMonitor.getLastUpdateTime().getTime();
                agentMonitor.setWorkingDuration(workingDuration);// 获取signoff前的状态的持续时间
                // TODO Save sign off time, sign duration time , the status and status duration time to db.
                while (ActionConstant.READY.equals(workingStatus)) {//如果sign off前状态为ready,ready个数减去一个
                    int readyNumber = instance.getReadyNumber();
                    instance.setReadyNumber(readyNumber - 1);
                }
            }
        }
        instance.setAgents(agents);
        if (instance.getMvtms() != null) {// Caceh has List<MvtmMonitor>
            instance = calculateData(instance);
        }
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);
        return instance;
    }

    /**
     * endcall 不改变agent状态,依旧是busy
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance endCall(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        Date now = new Date();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                // 上一个状态是接通视频， 计算在线开户时长
                long callDurationTime = now.getTime() - mvtmMonitor.getLastUpdateTime().getTime();
                mvtmMonitor.setCallDurationTime(callDurationTime);
                // TODO Save call duration time to db.
            }
        }
        instance = calculateData(instance);
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);
        return instance;
    }

    private MonitorInstance notReady(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        String action = event.getAction();// 获取到当前的action
        List<AgentMonitor> agents = instance.getAgents();
        for (AgentMonitor agentMonitor : agents) {
            if (event.getOwnerId().equals(agentMonitor.getStaffId())) {
                Date now = new Date();
                // 计算上一个状态的持续时间
                long workingDuration = now.getTime() - agentMonitor.getLastUpdateTime().getTime();
                agentMonitor.setWorkingDuration(workingDuration);
                //TODO Save before status duration to db.
                // 拿到上一次的状态
                String beforeStatus = agentMonitor.getWorkingStatus();
                while (beforeStatus.equals(ActionConstant.READY)) { // 如果上一个状态为ready
                    int readyNumber = instance.getReadyNumber();
                    int notReadyNumber = instance.getNotReadyNumber();
                    instance.setReadyNumber(readyNumber - 1);// readyNumber - 1
                    instance.setNotReadyNumber(notReadyNumber + 1);// notReadyNumber + 1
                    if (instance.getMvtms() != null) {// Caceh has List<MvtmMonitor>
                        instance = calculateData(instance);
                    }
                }
                // 如果上一个状态为busy ,当前状态为Miss
                while (beforeStatus.equals(ActionConstant.BUSY) && ActionConstant.MISS.equals(action)) {
                    int busyNumber = instance.getBusyNumber();
                    instance.setBusyNumber(busyNumber - 1);
                    if (instance.getMvtms() != null) {// Caceh has List<MvtmMonitor>
                        instance = calculateData(instance);
                    }
                }
                // 如果上一个状态为busy ,当前状态为ready
                while (beforeStatus.equals(ActionConstant.BUSY) && ActionConstant.READY.equals(action)) {
                    int busyNumber = instance.getBusyNumber();
                    instance.setBusyNumber(busyNumber - 1);// agent busy 数量减一
                    int readyNumber = instance.getReadyNumber();
                    instance.setReadyNumber(readyNumber + 1);// agent ready 数量加一
                    if (instance.getMvtms() != null) {// Caceh has List<MvtmMonitor>
                        instance = calculateData(instance);
                    }
                }
                // 设置当前状态
                agentMonitor.setWorkingStatus(action);
                // 设置当前状态的开始时间
                agentMonitor.setLastUpdateTime(now);
                Map<String, Integer> map = agentMonitor.getStatusTimes();
                Integer times = map.get(action);
                map.put(action, times + 1);
                agentMonitor.setStatusTimes(map);
                agents.add(agentMonitor);
                // Save data in db.
            }
        }
        instance.setAgents(agents);
        cache.put(CACHEKEY, instance);
        return instance;
    }
    
    private MonitorInstance exit(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(null);
                mvtmMonitor.setCallTimes(event.getCallTimes());
                mvtmMonitor.setRetryTimes(event.getRetryTimes());
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        // Send instance to WebSocket
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    private MonitorInstance calculateData(MonitorInstance instance) {
        List<MvtmMonitor> mvtms = instance.getMvtms();
        long maxWaitTime = instance.getMaxWaitTime();
        int totalCallingNumber = instance.getTotalCallingNumber();
        int moreThan30s = instance.getMoreThan30s();
        // Calculate
        long now = System.currentTimeMillis();// 当前时间
        for (MvtmMonitor mvtmMonitor : mvtms) {
            // 计算等待接听时间
            while (ActionConstant.VC_APPLY_S.equals(mvtmMonitor.getAction())
                    | ActionConstant.VC_RETRY.equals(mvtmMonitor.getAction())) {
                long difference = now - mvtmMonitor.getLastUpdateTime().getTime();
                long waitCallTime = mvtmMonitor.getWaitCallTime() + difference;
                mvtmMonitor.setWaitCallTime(waitCallTime);
                // 当前时间下,计算最长等候接入VTA时长
                maxWaitTime = maxWaitTime > waitCallTime ? maxWaitTime : waitCallTime;
            }
            long difference = now - mvtmMonitor.getLastUpdateTime().getTime();
            while (ActionConstant.VC_APPLY_S.equals(mvtmMonitor.getAction())
                    | ActionConstant.VC_RETRY.equals(mvtmMonitor.getAction()) && difference > 30 * 1000) {
                ++moreThan30s;
            }
        }
        double GOS = (totalCallingNumber - moreThan30s) / totalCallingNumber * 100;// 重新计算GOS
        instance.setMaxWaitTime(maxWaitTime);// 重新计算maxWaitTime
        instance.setGOS(GOS);
        return instance;
    }

}
