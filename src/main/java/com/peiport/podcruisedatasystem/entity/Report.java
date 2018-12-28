package com.peiport.podcruisedatasystem.entity;

import java.util.Date;

/**
 * 巡航检测报告对象
 */
public class Report {
    private String companyName; //单位名称
    private String office;   //局，如广州局
    private String voltage; //电压。单位KV
    private String routeName; //路线名
    private String towerNum; //塔号
    private String deviceName; //设备名
    private String imprefectLocaltion;  //缺陷位置
    private String kjgImageName; //可见光图片名称
    private Date kjgImageTime;  //可见光图片时间
    private String kjgOperator; //可见光操作人
    private String kjgImagePath ; //可见光图片路径
    private String hwImageName; //红外图片名称
    private Date hwImageTime;  //红外图片时间
    private String hwOperator; //红外操作人
    private String hwImagePath; //红外视频路径
    private String errorDescription; //故障描述
    private String handlerAdvice; //处理意见
    private String assayer;   //分析人
    private Date reportTime; //报告时间
    private String auditAdvice; //审核意见
    private String approveAdvice; //批准意见
    private String logoImagePath; //logo图片路径

    public String getLogoImagePath() {
        return logoImagePath;
    }

    public void setLogoImagePath(String logoImagePath) {
        this.logoImagePath = logoImagePath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getTowerNum() {
        return towerNum;
    }

    public void setTowerNum(String towerNum) {
        this.towerNum = towerNum;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getImprefectLocaltion() {
        return imprefectLocaltion;
    }

    public void setImprefectLocaltion(String imprefectLocaltion) {
        this.imprefectLocaltion = imprefectLocaltion;
    }

    public String getKjgImageName() {
        return kjgImageName;
    }

    public void setKjgImageName(String kjgImageName) {
        this.kjgImageName = kjgImageName;
    }

    public Date getKjgImageTime() {
        return kjgImageTime;
    }

    public void setKjgImageTime(Date kjgImageTime) {
        this.kjgImageTime = kjgImageTime;
    }

    public String getKjgOperator() {
        return kjgOperator;
    }

    public void setKjgOperator(String kjgOperator) {
        this.kjgOperator = kjgOperator;
    }

    public String getKjgImagePath() {
        return kjgImagePath;
    }

    public void setKjgImagePath(String kjgImagePath) {
        this.kjgImagePath = kjgImagePath;
    }

    public String getHwImageName() {
        return hwImageName;
    }

    public void setHwImageName(String hwImageName) {
        this.hwImageName = hwImageName;
    }

    public Date getHwImageTime() {
        return hwImageTime;
    }

    public void setHwImageTime(Date hwImageTime) {
        this.hwImageTime = hwImageTime;
    }

    public String getHwOperator() {
        return hwOperator;
    }

    public void setHwOperator(String hwOperator) {
        this.hwOperator = hwOperator;
    }

    public String getHwImagePath() {
        return hwImagePath;
    }

    public void setHwImagePath(String hwImagePath) {
        this.hwImagePath = hwImagePath;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getHandlerAdvice() {
        return handlerAdvice;
    }

    public void setHandlerAdvice(String handlerAdvice) {
        this.handlerAdvice = handlerAdvice;
    }

    public String getAssayer() {
        return assayer;
    }

    public void setAssayer(String assayer) {
        this.assayer = assayer;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getAuditAdvice() {
        return auditAdvice;
    }

    public void setAuditAdvice(String auditAdvice) {
        this.auditAdvice = auditAdvice;
    }

    public String getApproveAdvice() {
        return approveAdvice;
    }

    public void setApproveAdvice(String approveAdvice) {
        this.approveAdvice = approveAdvice;
    }

    @Override
    public String toString() {
        return "Report{" +
                "companyName='" + companyName + '\'' +
                ", office='" + office + '\'' +
                ", voltage='" + voltage + '\'' +
                ", routeName='" + routeName + '\'' +
                ", towerNum='" + towerNum + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", imprefectLocaltion='" + imprefectLocaltion + '\'' +
                ", kjgImageName='" + kjgImageName + '\'' +
                ", kjgImageTime=" + kjgImageTime +
                ", kjgOperator='" + kjgOperator + '\'' +
                ", kjgImagePath='" + kjgImagePath + '\'' +
                ", hwImageName='" + hwImageName + '\'' +
                ", hwImageTime=" + hwImageTime +
                ", hwOperator='" + hwOperator + '\'' +
                ", hwImagePath='" + hwImagePath + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", handlerAdvice='" + handlerAdvice + '\'' +
                ", assayer='" + assayer + '\'' +
                ", reportTime=" + reportTime +
                ", auditAdvice='" + auditAdvice + '\'' +
                ", approveAdvice='" + approveAdvice + '\'' +
                ", logoImagePath='" + logoImagePath + '\'' +
                '}';
    }
}
