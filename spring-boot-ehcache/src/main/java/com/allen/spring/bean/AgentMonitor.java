/**
 * 
 */
package com.allen.spring.bean;

/**
 * Monitor data
 * 
 * @author first
 *
 */
public class AgentMonitor {

    // agent staffId
    private String staffId;
    // agent staffName
    private String staffName;
    // VTA busy个数
    private Integer busyNumber;
    // VTA ready 个数
    private Integer readyNumber;
    // VTA not ready个数
    private Integer notReadyNumber;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getBusyNumber() {
        return busyNumber;
    }

    public void setBusyNumber(Integer busyNumber) {
        this.busyNumber = busyNumber;
    }

    public Integer getReadyNumber() {
        return readyNumber;
    }

    public void setReadyNumber(Integer readyNumber) {
        this.readyNumber = readyNumber;
    }

    public Integer getNotReadyNumber() {
        return notReadyNumber;
    }

    public void setNotReadyNumber(Integer notReadyNumber) {
        this.notReadyNumber = notReadyNumber;
    }

}
