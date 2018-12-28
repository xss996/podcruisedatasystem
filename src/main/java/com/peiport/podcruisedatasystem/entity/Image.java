package com.peiport.podcruisedatasystem.entity;

import java.util.Date;

public class Image {
    private Integer type; //图片类型
    private String office; //局，如广州局
    private String voltage; //电压。单位KV
    private String routeName; //路线名
    private String towerNum; //塔号
    private String deviceName; //设备名
    private String deviceLocaltion;  //设备坐标
    private Double  temperature;  //温度
    private String alarmType;   //报警类型
    private Date time;  //日期
    private String path; //路径
    private String imageName; //图片名称

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getDeviceLocaltion() {
        return deviceLocaltion;
    }

    public void setDeviceLocaltion(String deviceLocaltion) {
        this.deviceLocaltion = deviceLocaltion;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
