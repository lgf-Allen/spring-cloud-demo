package com.allen.spring.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Generate MVTM report forms.
 * 
 * @author first
 *
 */

public class Device implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7657306083603751734L;

    private String branchName;

    private String branchId;

    // mvtm 操作
    private String action;

    private String callNumber;

    private String transactionId;

    // mvtm 开始拨打电话时间
    private Date dialingStartTime;

    // mvtm 接通电话时间
    private Date dialingEndTime;

    // 拨打次数
    private int callTime;
    
    //标识mvtm状态
    private String status;

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

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDialingStartTime() {
        return dialingStartTime;
    }

    public void setDialingStartTime(Date dialingStartTime) {
        this.dialingStartTime = dialingStartTime;
    }

    public Date getDialingEndTime() {
        return dialingEndTime;
    }

    public void setDialingEndTime(Date dialingEndTime) {
        this.dialingEndTime = dialingEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCallTime() {
        return callTime;
    }

    public void setCallTime(int callTime) {
        this.callTime = callTime;
    }

}
