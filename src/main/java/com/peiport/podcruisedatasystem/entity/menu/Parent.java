package com.peiport.podcruisedatasystem.entity.menu;

import java.util.List;

public class Parent {
    private String title;
    private List<Child> childList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }


}
