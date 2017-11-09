package com.wt.lbsWeb.entity;

import java.util.Date;

/**
 * AOI基本信息
 * Created by Administrator on 2017/11/9.
 */
public class AOIInfoEntity {

    private String aoiId;// AOI数据信息ID（uid）
    private String provinceId;// AOI数据信息所在省
    private String cityId;// AOI数据信息所在城市
    private String address;// AOI数据信息所在地址
    private String regionId;// AOI数据信息所属区域ID（百度）
    private String regionName;// AOI数据信息所属区域名称
    private String label;// 原始标签
    private String aoiName;// AOI数据信息名称
    private String regionAoi;// AOI范围信息
    private String instruction;// AOI信息说明
    private String customLabel;// 根据数据自己做成的标签
    private String source;// BD：百度；GD：高德
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String delFlag;// 是否废弃 1：废弃； 0：使用中

    public String getAoiId() {
        return aoiId;
    }

    public void setAoiId(String aoiId) {
        this.aoiId = aoiId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAoiName() {
        return aoiName;
    }

    public void setAoiName(String aoiName) {
        this.aoiName = aoiName;
    }

    public String getRegionAoi() {
        return regionAoi;
    }

    public void setRegionAoi(String regionAoi) {
        this.regionAoi = regionAoi;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel(String customLabel) {
        this.customLabel = customLabel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        return "AOIInfoEntity{" +
                "aoiId='" + aoiId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", address='" + address + '\'' +
                ", regionId='" + regionId + '\'' +
                ", regionName='" + regionName + '\'' +
                ", label='" + label + '\'' +
                ", aoiName='" + aoiName + '\'' +
                ", regionAoi='" + regionAoi + '\'' +
                ", instruction='" + instruction + '\'' +
                ", customLabel='" + customLabel + '\'' +
                ", source='" + source + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
