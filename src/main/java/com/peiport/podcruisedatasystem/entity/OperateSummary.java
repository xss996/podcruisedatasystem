package com.peiport.podcruisedatasystem.entity;

import java.util.Date;

/**
 * 操作纪要pojo类
 */
public class OperateSummary {
    private Integer id;
    private Date date;
    private String towerNum;  //塔号
    private Integer pollingNum; //巡检序号
    private String eventClass;  //事件分类
    private String eventContent; //事件内容
    private String videoFileName;  //视频文件名称
    private String imageFileName;  //图片文件名称

    public OperateSummary() {
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

    public String getTowerNum() {
        return towerNum;
    }

    public void setTowerNum(String towerNum) {
        this.towerNum = towerNum;
    }

    public Integer getPollingNum() {
        return pollingNum;
    }

    public void setPollingNum(Integer pollingNum) {
        this.pollingNum = pollingNum;
    }

    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
