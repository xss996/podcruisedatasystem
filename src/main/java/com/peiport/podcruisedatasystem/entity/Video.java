package com.peiport.podcruisedatasystem.entity;

public class Video {
    private String name;
    private String url;
    private String size;

    public Video() {
    }

    public Video(String name, String url, String size) {
        this.name = name;
        this.url = url;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
