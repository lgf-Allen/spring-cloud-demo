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

    // 身份证扫描时间
    private Date scanTime;

    private Date lastUpdateTime;

    // 填写form表单时间
    private long fillDurationTime;

    // 预留,等候接听所需时间
    private long waitCallTime;
    // 拨打次数
    private int callTimes;
    // 超过30s未接通次数
    private int retryTimes;
    // 标识mvtm状态
    private String status;
    // 成功开户个数
    //private int openAccountSuccessTimes;
    // 在线开户时长:视频接通到挂断
    private long callDurationTime;

//    // 成功接入次数
//    private int callSuccessTimes;

    // 等待接通所需时间
    private long waitCallSuccessTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getFillDurationTime() {
        return fillDurationTime;
    }

    public void setFillDurationTime(long fillDurationTime) {
        this.fillDurationTime = fillDurationTime;
    }

    public long getWaitCallTime() {
        return waitCallTime;
    }

    public void setWaitCallTime(long waitCallTime) {
        this.waitCallTime = waitCallTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public int getOpenAccountSuccessTimes() {
//        return openAccountSuccessTimes;
//    }
//
//    public void setOpenAccountSuccessTimes(int openAccountSuccessTimes) {
//        this.openAccountSuccessTimes = openAccountSuccessTimes;
//    }

//    public int getCallSuccessTimes() {
//        return callSuccessTimes;
//    }
//
//    public void setCallSuccessTimes(int callSuccessTimes) {
//        this.callSuccessTimes = callSuccessTimes;
//    }

    public long getCallDurationTime() {
        return callDurationTime;
    }

    public void setCallDurationTime(long callDurationTime) {
        this.callDurationTime = callDurationTime;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public long getWaitCallSuccessTime() {
        return waitCallSuccessTime;
    }

    public void setWaitCallSuccessTime(long waitCallSuccessTime) {
        this.waitCallSuccessTime = waitCallSuccessTime;
    }

}
