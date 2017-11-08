package com.wt.lbsWeb.entity;

import java.io.Serializable;

/**
 * 区域关系实体类
 * Created by Administrator on 2017/11/8.
 */
public class AreaRegionEntity implements Serializable {

    private String id;
    private String name;
    private String parentId;
    private String levelType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    @Override
    public String toString() {
        return "AreaRegionEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", levelType='" + levelType + '\'' +
                '}';
    }
}
