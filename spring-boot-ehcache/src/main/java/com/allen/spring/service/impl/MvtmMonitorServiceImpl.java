/**
 * 
 */
package com.allen.spring.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.allen.spring.service.MvtmMonitorService;

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;

/**
 * @author first
 *
 */
@Service
public class MvtmMonitorServiceImpl implements MvtmMonitorService {

    private static Logger logger = LoggerFactory.getLogger(MvtmMonitorServiceImpl.class);

    private static final String CACHEKEY = "cacheKey";
    @Resource
    private CacheManager manager;

    @Override
    public MonitorInstance update(ActionEvent event) {
        Cache cache = manager.getCache("mvtmEhcache");
        MonitorInstance instance = null;
        // MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
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
        case ActionConstant.FORM_E:
            instance = fillFormEnd(cache, event);
            break;
        case ActionConstant.VC_APPLY_S:
            instance = connectVideoStart(cache, event);
            break;
        case ActionConstant.VC_CONN:
            instance = connectVideoSuccess(cache ,event);
            break;
        case ActionConstant.VC_RETRY:
            instance = connectVideoRetry(cache , event);
            break;
        case ActionConstant.VC_DISCONN:
            break;

        case ActionConstant.SIGNON:
            instance = signOn(cache , event);
            break;
        case ActionConstant.SIGNOFF:
            instance = signOff(cache , event);
            break;
        case ActionConstant.LUNCH:
            instance = notReady(cache , event);
            break;
        case ActionConstant.MEETING:
            instance = notReady(cache , event);
            break;
        case ActionConstant.TRAINING:
            instance = notReady(cache , event);
            break;
        case ActionConstant.BREAK:
            instance = notReady(cache , event);
            break;
        case ActionConstant.CHECKER:
            instance = notReady(cache , event);
            break;
        case ActionConstant.CASEFU:
            instance = notReady(cache , event);
            break;
        case ActionConstant.MISS:
            instance = notReady(cache , event);
            break;
        case ActionConstant.BUSY:
            instance = notReady(cache , event);
            break;
        case ActionConstant.READY:
            instance = notReady(cache , event);
            break;
        case ActionConstant.ENDCALL:
            instance = endCall(cache , event);
            break;
        default :
            
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
        MvtmMonitor mvtm = new MvtmMonitor();
        mvtm.setBranchId(event.getOwnerId());
        mvtm.setBranchName(event.getOwnerName());
        mvtm.setAction(event.getAction());
        
        List<MvtmMonitor> mvtms = null;
        if (instance != null && instance.getMvtms() != null) {// Cache has List<MvtmMonitor>
            mvtms = instance.getMvtms();
            mvtms.add(mvtm);
            instance.setMvtms(mvtms);
            instance = calculateData(instance);
        } else if (instance == null ) { //No cache
            instance = new MonitorInstance();
            mvtms = new ArrayList<MvtmMonitor>();
            mvtms.add(mvtm);
            instance.setMvtms(mvtms);
        } else {// instance != null && instance.getMvtms = null
            mvtms = new ArrayList<MvtmMonitor>();
            mvtms.add(mvtm);
            instance.setMvtms(mvtms);
        }
        cache.put(CACHEKEY, instance);
        return instance;
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
            if (event.getId().equals(mvtmMonitor.getId())) {
                mvtmMonitor.setAction(event.getAction());
                mvtmMonitor.setTransactionId(event.getTransactionId());
                mvtmMonitor.setLastUpdateTime(new Date());
                mvtms.add(mvtmMonitor);
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
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
                mvtms.add(mvtmMonitor);
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is FORM_E.
     * 
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance fillFormEnd(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<MvtmMonitor> mvtms = instance.getMvtms();
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                // Calculate filling form time.
                long fillDurationTime = System.currentTimeMillis() - mvtmMonitor.getLastUpdateTime().getTime();
                mvtmMonitor.setFillDurationTime(fillDurationTime);
                mvtmMonitor.setLastUpdateTime(new Date());
                mvtms.add(mvtmMonitor);
            }
        }
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
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
        for (MvtmMonitor mvtmMonitor : mvtms) {
            if (event.getTransactionId().equals(mvtmMonitor.getTransactionId())) {
                mvtmMonitor.setAction(event.getAction());
                mvtmMonitor.setCallTimes(event.getCallTimes());
                mvtmMonitor.setLastUpdateTime(new Date());
                mvtms.add(mvtmMonitor);
            }
        }
        int waitIncomingNumber = instance.getWaitIncomingNumber();
        instance.setWaitIncomingNumber(waitIncomingNumber + 1);
        int totalCallingNumber = instance.getTotalCallingNumber();
        instance.setTotalCallingNumber(totalCallingNumber + 1);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }
    
    /**
     * Action is VC_CONN.
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
                long waitCallSuccessTime = System.currentTimeMillis() - mvtmMonitor.getLastUpdateTime().getTime();
                mvtmMonitor.setWaitCallSuccessTime(waitCallSuccessTime);
                mvtms.add(mvtmMonitor);
            }
        }
        int waitIncomingNumber = instance.getWaitIncomingNumber();
        instance.setWaitIncomingNumber(waitIncomingNumber - 1);
        int successCallingNumber = instance.getSucessCallingNumber();
        instance.setSucessCallingNumber(successCallingNumber + 1);
        int totalCallingNumber = instance.getTotalCallingNumber();
        double ABN = (totalCallingNumber-(successCallingNumber + 1) ) / totalCallingNumber * 100;
        instance.setABN(ABN);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }

    /**
     * Action is VC_RETRY.
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
                mvtmMonitor.setLastUpdateTime(new Date());
                mvtms.add(mvtmMonitor);
            }
        }
        int totalCallingNumber = instance.getTotalCallingNumber();
        instance.setTotalCallingNumber(totalCallingNumber + 1);
        instance.setMvtms(mvtms);
        instance = calculateData(instance);
        cache.put(CACHEKEY, instance);// Update cache
        return instance;
    }
    
    /**
     * Action is SIGNON.
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance signOn(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        
        AgentMonitor agent = new AgentMonitor();
        agent.setStaffId(event.getOwnerId());
        agent.setStaffName(event.getOwnerName());
        agent.setSignOnTime(new Date());
        agent.setTransactionId(event.getTransactionId());
        agent.setWorkingStatus(ActionConstant.READY);//Ready status
        agent.setLastUpdateTime(new Date());//Ready status start time
        List<AgentMonitor> agents = null;
        if (instance != null && instance.getAgents() != null) {// Cache has List<AgentMonitor>
            agents = instance.getAgents();
            for (AgentMonitor agentMonitor : agents) {
                if(event.getOwnerId().equals(agentMonitor.getStaffId())) {
                    Map<String ,Integer > map = agentMonitor.getStatusTimes();
                    Integer times = map.get(ActionConstant.READY);
                    map.put(ActionConstant.READY, times+1);
                    agent.setStatusTimes(map);
                }
            }
            
        } else if (instance == null) {
            instance = new MonitorInstance();
            agents = new ArrayList<AgentMonitor>();
        } else {
            agents = new ArrayList<AgentMonitor>();
        }
        agents.add(agent);
        instance.setAgents(agents);
        int readyNumber = instance.getReadyNumber();
        instance.setReadyNumber(readyNumber+1);
        if(instance.getMvtms() != null) {//Caceh has List<MvtmMonitor>
            instance = calculateData(instance);
        }
        cache.put(CACHEKEY, instance);
        return instance;
    }
    
    /**
     * Action is SIGNOFF.
     * @param cache
     * @param event
     * @return
     */
    private MonitorInstance signOff(Cache cache, ActionEvent event) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        List<AgentMonitor> agents = instance.getAgents();
        for (AgentMonitor agentMonitor : agents) {
            if(event.getOwnerId().equals(agentMonitor.getStaffId())){
                Date now = new Date();
                agentMonitor.setSignOffTime(now);//设置sign off时间
                long signDuration = now.getTime() - agentMonitor.getSignOnTime().getTime();
                agentMonitor.setSignDuration(signDuration);//计算sign on时间
                String workingStatus = agentMonitor.getWorkingStatus();
                long workingDuration = agentMonitor.getLastUpdateTime().getTime() - now.getTime();
                agentMonitor.setWorkingDuration(workingDuration);//获取signoff前的状态的持续时间
                //Save data in db.
                agents.add(agentMonitor);
            }
        }
        instance.setAgents(agents);
        int readyNumber = instance.getReadyNumber();
        instance.setReadyNumber(readyNumber+1);
        if(instance.getMvtms() != null) {//Caceh has List<MvtmMonitor>
            instance = calculateData(instance);
        }
        cache.put(CACHEKEY, instance);
        return instance;
    }
    
    private MonitorInstance endCall(Cache cache, ActionEvent event) {
        
        return null;
    }
    
    
    private MonitorInstance notReady(Cache cache , ActionEvent event ) {
        MonitorInstance instance = cache.get(CACHEKEY, MonitorInstance.class);
        String action = event.getAction();//获取到当前的action
        List<AgentMonitor> agents = instance.getAgents();
        for (AgentMonitor agentMonitor : agents) {
            if(event.getTransactionId().equals(agentMonitor.getTransactionId())){
                Date now = new Date();
                //计算上一个状态的持续时间
                long workingDuration = now.getTime() - agentMonitor.getLastUpdateTime().getTime();
                agentMonitor.setWorkingDuration(workingDuration);
                //拿到上一次的状态
                String beforeStatus = agentMonitor.getWorkingStatus();
                while(beforeStatus.equals(ActionConstant.READY)) { //如果上一个状态为ready
                    int readyNumber = instance.getReadyNumber();
                    int notReadyNumber = instance.getNotReadyNumber();
                    instance.setReadyNumber(readyNumber-1);//readyNumber - 1
                    instance.setNotReadyNumber(notReadyNumber+1);// notReadyNumber + 1
                    if(instance.getMvtms() != null) {//Caceh has List<MvtmMonitor>
                        instance = calculateData(instance);
                    }
                }
                //如果上一个状态为busy ,当前状态为Miss
                while(beforeStatus.equals(ActionConstant.BUSY) && ActionConstant.MISS.equals(action)) {
                    int busyNumber = instance.getBusyNumber();
                    instance.setBusyNumber(busyNumber - 1);
                    if(instance.getMvtms() != null) {//Caceh has List<MvtmMonitor>
                        instance = calculateData(instance);
                    }
                }
                //如果上一个状态为busy ,当前状态为ready
                while(beforeStatus.equals(ActionConstant.BUSY) && ActionConstant.READY.equals(action)) {
                    int busyNumber = instance.getBusyNumber();
                    instance.setBusyNumber(busyNumber - 1);//agent busy 数量减一
                    int readyNumber = instance.getReadyNumber();
                    instance.setReadyNumber(readyNumber + 1);//agent ready 数量加一
                    if(instance.getMvtms() != null) {//Caceh has List<MvtmMonitor>
                        instance = calculateData(instance);
                    }
                }
                //设置当前状态
                agentMonitor.setWorkingStatus(action);
                //设置当前状态的开始时间
                agentMonitor.setLastUpdateTime(now);
                Map<String ,Integer > map = agentMonitor.getStatusTimes();
                Integer times = map.get(action);
                map.put(action, times+1);
                agentMonitor.setStatusTimes(map);
                agents.add(agentMonitor);
                //Save data in db.
            }
        }
        instance.setAgents(agents);
        cache.put(CACHEKEY, instance);
        return instance;
    }

    
    
    private MonitorInstance calculateData(MonitorInstance instance) {
        List<MvtmMonitor> mvtms = instance.getMvtms();
        long maxWaitTime = instance.getMaxWaitTime();
        int callTime = instance.getTotalCallingNumber();
        int moreThan30s = instance.getMoreThan30s();
        // Calculate
        for (MvtmMonitor mvtmMonitor : mvtms) {
            long difference = System.currentTimeMillis() - mvtmMonitor.getLastUpdateTime().getTime();
            while (ActionConstant.VC_APPLY_S.equals(mvtmMonitor.getAction())
                    | ActionConstant.VC_RETRY.equals(mvtmMonitor.getAction()) && difference > maxWaitTime) {
                maxWaitTime = difference;
            }
            while (ActionConstant.VC_APPLY_S.equals(mvtmMonitor.getAction())
                    | ActionConstant.VC_RETRY.equals(mvtmMonitor.getAction()) && difference > 30 * 1000) {
                ++moreThan30s;
            }
        }
        double GOS = (callTime - moreThan30s) / callTime * 100;// 重新计算GOS
        instance.setMaxWaitTime(maxWaitTime);// 重新计算maxWaitTime
        instance.setGOS(GOS);
        return instance;
    }

}
