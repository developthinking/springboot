package com.wt.lbsWeb.entity;

import java.util.Date;

/**
 * 标签
 * Created by Administrator on 2017/11/9.
 */
public class AreaLabelEntity {

    private String labelId;// 标签ID
    private String labelName;// 标签名称
    private String parentId;// 父ID
    private String levelType;// 层级（1，2，3级）
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String delFlag;// 是否废弃 1：废弃； 0：使用中

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "AreaLabelEntity{" +
                "labelId='" + labelId + '\'' +
                ", labelName='" + labelName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", levelType='" + levelType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
