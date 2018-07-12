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
    private int waitIncomingNumber;

    // VTM等候接入VTA时长
    private long maxWaitTime;

    // 超过30秒未接入的个数
    private int moreThan30s;

    // 拨入总数
    private int totalCallingNumber;

    // 成功接入总数
    private int sucessCallingNumber;

    // VTA busy个数
    private int busyNumber;

    // VTA ready 个数
    private int readyNumber;

    // VTA not ready个数
    private int notReadyNumber;

    // 拨入的总数 - 超过30秒未能接入的个数/拨入的总数*100%
    private double GOS;

    // 由当日拨入总数 – 成功接入总数 / 拨入总数*100%
    private double ABN;

    public int getWaitIncomingNumber() {
        return waitIncomingNumber;
    }

    public void setWaitIncomingNumber(int waitIncomingNumber) {
        this.waitIncomingNumber = waitIncomingNumber;
    }

    public long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getMoreThan30s() {
        return moreThan30s;
    }

    public void setMoreThan30s(int moreThan30s) {
        this.moreThan30s = moreThan30s;
    }

    public int getTotalCallingNumber() {
        return totalCallingNumber;
    }

    public void setTotalCallingNumber(int totalCallingNumber) {
        this.totalCallingNumber = totalCallingNumber;
    }

    public int getSucessCallingNumber() {
        return sucessCallingNumber;
    }

    public void setSucessCallingNumber(int sucessCallingNumber) {
        this.sucessCallingNumber = sucessCallingNumber;
    }

    public int getBusyNumber() {
        return busyNumber;
    }

    public void setBusyNumber(int busyNumber) {
        this.busyNumber = busyNumber;
    }

    public int getReadyNumber() {
        return readyNumber;
    }

    public void setReadyNumber(int readyNumber) {
        this.readyNumber = readyNumber;
    }

    public int getNotReadyNumber() {
        return notReadyNumber;
    }

    public void setNotReadyNumber(int notReadyNumber) {
        this.notReadyNumber = notReadyNumber;
    }

    public double getGOS() {
        return GOS;
    }

    public void setGOS(double gOS) {
        GOS = gOS;
    }

    public double getABN() {
        return ABN;
    }

    public void setABN(double aBN) {
        ABN = aBN;
    }

}
