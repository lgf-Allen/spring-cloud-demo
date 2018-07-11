/**
 * 
 */
package com.allen.spring.bean;

import java.util.Date;

/**
 * Generate VTA report forms.
 * 
 * @author first
 *
 */
public class AgentPO {

    // agent staffId
    private String staffId;
    // agent staffName
    private String staffName;
    // Sign on;Sign off
    private String signStatus;
    // Sign on time
    private Date signOnTime;
    // Sign off time
    private Date signOffTime;
    // SignOffTime - SignOnTime
    private Long signDuration;
    // Working
    // status:Avaliable,notAvaliable(Lunch,Meeting,Traning,Break,Checker,Talking,Case
    // Follow Up)
    private String workingStatus;
    // Working status startTime
    private Date lastUpdateTime;
    // EndTime-StartTime
    private Long workingDuration;

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

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public Date getSignOnTime() {
        return signOnTime;
    }

    public void setSignOnTime(Date signOnTime) {
        this.signOnTime = signOnTime;
    }

    public Date getSignOffTime() {
        return signOffTime;
    }

    public void setSignOffTime(Date signOffTime) {
        this.signOffTime = signOffTime;
    }

    public Long getSignDuration() {
        return signDuration;
    }

    public void setSignDuration(Long signDuration) {
        this.signDuration = signDuration;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getWorkingDuration() {
        return workingDuration;
    }

    public void setWorkingDuration(Long workingDuration) {
        this.workingDuration = workingDuration;
    }

}
