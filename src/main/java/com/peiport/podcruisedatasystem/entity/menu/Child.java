package com.peiport.podcruisedatasystem.entity.menu;

public class Child {
    private String name;
    private int id;
    private String path;
    private int type;

    public Child() {
    }

    public Child(String name, int id,String path,int type) {
        this.name = name;
        this.id = id;
        this.path = path;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Child{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", path='" + path + '\'' +
                ", type=" + type +
                '}';
    }
}
