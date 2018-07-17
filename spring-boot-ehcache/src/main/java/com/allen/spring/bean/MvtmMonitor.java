package com.allen.spring.bean;

import java.util.Date;

/**
 * Generate MVTM report forms.
 * 
 * @author first
 *
 */

public class MvtmMonitor {

    private Long id;

    private String branchName;

    private String branchId;

    private String action;

    private String transactionId;

    // private Date startTime;

    private Date lastUpdateTime;

    private Long fillDurationTime;

    // 预留,本次call接通所需时间
    private Long waitCallSuccessTime;
    // 拨打次数
    private int callTimes;

    private int retryTimes;
    // 标识mvtm状态
    private String status;

    private int successTime;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // public Date getStartTime() {
    // return startTime;
    // }
    //
    // public void setStartTime(Date startTime) {
    // this.startTime = startTime;
    // }
    //
    // public Date getEndTime() {
    // return endTime;
    // }
    //
    // public void setEndTime(Date endTime) {
    // this.endTime = endTime;
    // }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFillDurationTime() {
        return fillDurationTime;
    }

    public void setFillDurationTime(Long fillDurationTime) {
        this.fillDurationTime = fillDurationTime;
    }

    public int getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(int successTime) {
        this.successTime = successTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getCallTimes() {
        return callTimes;
    }

    public void setCallTimes(int callTimes) {
        this.callTimes = callTimes;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Long getWaitCallSuccessTime() {
        return waitCallSuccessTime;
    }

    public void setWaitCallSuccessTime(Long waitCallSuccessTime) {
        this.waitCallSuccessTime = waitCallSuccessTime;
    }

}
