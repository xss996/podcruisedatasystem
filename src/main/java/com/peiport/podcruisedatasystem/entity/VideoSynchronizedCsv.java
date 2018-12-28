package com.peiport.podcruisedatasystem.entity;

import java.util.Date;
import java.util.function.DoubleBinaryOperator;

/**
 * 视频同步csv对象
 */
public class VideoSynchronizedCsv {
    private Integer id;   //序列
    private Date date;   //时间
    private Integer pollingNum;  //巡检序
    private String towerNum;  //塔号
    private Double podBearing; //吊舱方位
    private Double podPitching;  //吊舱俯仰
    private Double podRolling; //吊舱横滚
    private Double visibleLightAngle; //可见光角度
    private Double IRAngle; //红外角度
    private Integer currentChannel; //当前通道
    private Double GPSLng; //gps经度
    private Double GPSLat;  //gps纬度
    private Double GPSHeight;  //gps高度
    private Double planeDirection; //飞机方向
    private Double planePitching; //飞机俯仰
    private Double planeRolling;  //飞机横滚
    private Double boresightDirection; //视轴方向
    private Double boresightPitching;  //视轴俯仰
    private Double boresightRolling; //视轴横滚
    private Double targetLng ; //目标经度
    private Double targetLat ; //目标纬度
    private Double groundDistance; //目标地面距离
    private Double laserDistance;  //目标激光距离
    private Double surfaceHeight; //地表高度

    public Double getPlaneRolling() {
        return planeRolling;
    }

    public void setPlaneRolling(Double planePolling) {
        this.planeRolling = planePolling;
    }

    public Double getBoresightPitching() {
        return boresightPitching;
    }

    public void setBoresightPitching(Double boresightPitching) {
        this.boresightPitching = boresightPitching;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPollingNum() {
        return pollingNum;
    }

    public void setPollingNum(Integer pollingNum) {
        this.pollingNum = pollingNum;
    }

    public String getTowerNum() {
        return towerNum;
    }

    public void setTowerNum(String towerNum) {
        this.towerNum = towerNum;
    }

    public Double getPodBearing() {
        return podBearing;
    }

    public void setPodBearing(Double podBearing) {
        this.podBearing = podBearing;
    }

    public Double getPodPitching() {
        return podPitching;
    }

    public void setPodPitching(Double podPitching) {
        this.podPitching = podPitching;
    }

    public Double getPodRolling() {
        return podRolling;
    }

    public void setPodRolling(Double podRolling) {
        this.podRolling = podRolling;
    }

    public Double getVisibleLightAngle() {
        return visibleLightAngle;
    }

    public void setVisibleLightAngle(Double visibleLightAngle) {
        this.visibleLightAngle = visibleLightAngle;
    }

    public Double getIRAngle() {
        return IRAngle;
    }

    public void setIRAngle(Double IRAngle) {
        this.IRAngle = IRAngle;
    }

    public Integer getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Integer currentChannel) {
        this.currentChannel = currentChannel;
    }

    public Double getGPSLng() {
        return GPSLng;
    }

    public void setGPSLng(Double GPSLng) {
        this.GPSLng = GPSLng;
    }

    public Double getGPSLat() {
        return GPSLat;
    }

    public void setGPSLat(Double GPSLat) {
        this.GPSLat = GPSLat;
    }

    public Double getGPSHeight() {
        return GPSHeight;
    }

    public void setGPSHeight(Double GPSHeight) {
        this.GPSHeight = GPSHeight;
    }

    public Double getPlaneDirection() {
        return planeDirection;
    }

    public void setPlaneDirection(Double planeDirection) {
        this.planeDirection = planeDirection;
    }

    public Double getPlanePitching() {
        return planePitching;
    }

    public void setPlanePitching(Double planePitching) {
        this.planePitching = planePitching;
    }

    public Double getBoresightDirection() {
        return boresightDirection;
    }

    public void setBoresightDirection(Double boresightDirection) {
        this.boresightDirection = boresightDirection;
    }

    public Double getBoresightRolling() {
        return boresightRolling;
    }

    public void setBoresightRolling(Double boresightRolling) {
        this.boresightRolling = boresightRolling;
    }

    public Double getTargetLng() {
        return targetLng;
    }

    public void setTargetLng(Double targetLng) {
        this.targetLng = targetLng;
    }

    public Double getTargetLat() {
        return targetLat;
    }

    public void setTargetLat(Double targetLat) {
        this.targetLat = targetLat;
    }

    public Double getGroundDistance() {
        return groundDistance;
    }

    public void setGroundDistance(Double groundDistance) {
        this.groundDistance = groundDistance;
    }

    public Double getLaserDistance() {
        return laserDistance;
    }

    public void setLaserDistance(Double laserDistance) {
        this.laserDistance = laserDistance;
    }

    public Double getSurfaceHeight() {
        return surfaceHeight;
    }

    public void setSurfaceHeight(Double surfaceHeight) {
        this.surfaceHeight = surfaceHeight;
    }

}
