/**
 * 
 */
package com.allen.spring.bean;

/**
 * @author first
 *
 */
public class MvtmMonitor {

    // 等待接入VTA数量
    private Integer waitIncomingNumber;

    // VTM等候接入VTA时长
    private Long maxWaitTime;

    // 超过30秒未接入的个数
    private Integer moreThan30s;

    // 拨入总数
    private Integer totalCallingNumber;

    // 成功接入总数
    private Integer sucessCallingNumber;

    public Integer getWaitIncomingNumber() {
        return waitIncomingNumber;
    }

    public void setWaitIncomingNumber(Integer waitIncomingNumber) {
        this.waitIncomingNumber = waitIncomingNumber;
    }

    public Long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Integer getMoreThan30s() {
        return moreThan30s;
    }

    public void setMoreThan30s(Integer moreThan30s) {
        this.moreThan30s = moreThan30s;
    }

    public Integer getTotalCallingNumber() {
        return totalCallingNumber;
    }

    public void setTotalCallingNumber(Integer totalCallingNumber) {
        this.totalCallingNumber = totalCallingNumber;
    }

    public Integer getSucessCallingNumber() {
        return sucessCallingNumber;
    }

    public void setSucessCallingNumber(Integer sucessCallingNumber) {
        this.sucessCallingNumber = sucessCallingNumber;
    }

}
