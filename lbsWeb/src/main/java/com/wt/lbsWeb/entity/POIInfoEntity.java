package com.wt.lbsWeb.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * POI基本信息
 * Created by Administrator on 2017/11/9.
 */
public class POIInfoEntity implements Serializable {

    private String poiId;//   POI数据信息ID（uid）
    private String provinceId;//   POI数据信息所在省
    private String cityId;//   POI数据信息所在城市
    private String address;//   POI数据信息所在地址
    private String regionId;//   POI数据信息所属区域ID（百度）
    private String regionName;//   POI数据信息所属区域名称
    private String label;//   原始标签
    private String poiName;//   POI数据信息名称
    private Double lngCenter;//   POI经度
    private Double latCenter;//   POI纬度
    private String type;//   0:POI;  1:AOI
    private String regionAoi;//   AOI范围信息
    private String instruction;//   POI信息说明
    private String customLabel;//   根据数据自己做成的标签
    private String source;//   BD：百度；GD：高德
    private Date createTime;//   创建时间
    private Date updateTime;//   更新时间
    private String delFlag;//   是否废弃  1：废弃； 0：使用中

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
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

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public Double getLngCenter() {
        return lngCenter;
    }

    public void setLngCenter(Double lngCenter) {
        this.lngCenter = lngCenter;
    }

    public Double getLatCenter() {
        return latCenter;
    }

    public void setLatCenter(Double latCenter) {
        this.latCenter = latCenter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "POIInfoEntity{" +
                "poiId='" + poiId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", address='" + address + '\'' +
                ", regionId='" + regionId + '\'' +
                ", regionName='" + regionName + '\'' +
                ", label='" + label + '\'' +
                ", poiName='" + poiName + '\'' +
                ", lngCenter=" + lngCenter +
                ", latCenter=" + latCenter +
                ", type='" + type + '\'' +
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
