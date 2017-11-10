package com.wt.lbsWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.wt.lbsWeb.base.constant.PoiAoiConstant;
import com.wt.lbsWeb.base.page.PageInfo;
import com.wt.lbsWeb.base.service.PoiAoiBaseService;
import com.wt.lbsWeb.base.service.PoiAoiRedisService;
import com.wt.lbsWeb.base.util.CommonMethod;
import com.wt.lbsWeb.base.util.UTF8Util;
import com.wt.lbsWeb.base.web.BaseAction;
import com.wt.lbsWeb.entity.AOIInfoEntity;
import com.wt.lbsWeb.entity.AreaLabelEntity;
import com.wt.lbsWeb.entity.POIInfoEntity;
import com.wt.lbsWeb.service.AOIService;
import com.wt.lbsWeb.service.AreaLabelService;
import com.wt.lbsWeb.service.AreaRegionService;
import com.wt.lbsWeb.service.POIService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 搜索POI AOI
 * Created by Administrator on 2017/11/9.
 */
@RestController
@RequestMapping(value = "/coordinate")
public class PoiAoiSearchController extends BaseAction {

    @Autowired
    private POIService poiService;
    @Autowired
    private AOIService aoiService;
    @Autowired
    private AreaLabelService areaLabelService;
    @Autowired
    private AreaRegionService areaRegionService;
    @Autowired
    private PoiAoiBaseService poiAoiBaseService;
    @Autowired
    private PoiAoiRedisService poiAoiRedisService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageInfo<POIInfoEntity> list(HttpServletRequest request) {
        int pageNo = ServletRequestUtils.getIntParameter(request, "page", 1);
        int pageSize = ServletRequestUtils.getIntParameter(request, "size", 20);
        String province = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "province", ""));
        String city = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "city", ""));
        String label = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "label", ""));
        String name = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "name", ""));

        POIInfoEntity param = new POIInfoEntity();
        param.setProvinceId(province);
        param.setCityId(city);
        param.setLabel(label);
        param.setPoiName(name);
        param.setType("0");
        param.setDelFlag("0");

        try {
            Page<POIInfoEntity> poiInfoEntities = poiService.findByPage(param, pageNo, pageSize);
            PageInfo<POIInfoEntity> pageInfo = new PageInfo<>(poiInfoEntities);
            return pageInfo;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 需求一：根据名字或标签查找POI AOI
     */
    @RequestMapping(value = "/fun1", method = RequestMethod.GET)
    public JSONObject searchByNameOrLabel(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 获取参数
        String province = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "province", ""));
        String city = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "city", ""));
        String label = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "label", ""));
        String name = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "name", ""));
        String to = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "to", "0")); // 0:POI;1:AOI(默认to=0)
        int pageNumber = 1;
        int pageSize = 20;
        try {
            pageNumber = ServletRequestUtils.getIntParameter(request, "page", 1);
            pageSize = ServletRequestUtils.getIntParameter(request, "size", 20);
        } catch (Exception e) {
            result.put(STATUS, 11);
            result.put(MSG, "page或size参数非法");
            return result;
        }
        // 判断参数
        if (!areaRegionService.fitProvinceCity(province, city)) {
            result.put(STATUS, 26);
            result.put(MSG, "省市ID非法");
            return result;
        }
        if (StringUtils.isBlank(label) && StringUtils.isBlank(name)) {
            result.put(STATUS, 27);
            result.put(MSG, "标签和名称为空");
            return result;
        }
        if (!"0".equals(to) && !"1".equals(to)) {
            result.put(STATUS, 22);
            result.put(MSG, "to非法");
            return result;
        }
        // 查询
        try {
            List<Map<String, String>> datas = new ArrayList<>();
            if (StringUtils.isBlank(name)) { // 名称为空
                long total = 0;
                Set<String> uids = new HashSet<String>();
                AreaLabelEntity areaPoiLable = areaLabelService.findById(label);
                if (areaPoiLable == null) {
                    result.put(STATUS, 24);
                    result.put(MSG, "标签非法");
                    return result;
                }

                List<String> labels = areaLabelService.getLabels(areaPoiLable);
                if ("0".equals(to)) { //to=0 POI
                    uids = poiAoiRedisService.getUidsByCategories(PoiAoiConstant.POI_CATEGORY_PRE, labels, province, city, uids);
                } else { //to=1 AOI
                    uids = poiAoiRedisService.getUidsByCategories(PoiAoiConstant.AOI_CATEGORY_PRE, labels, province, city, uids);
                }

                total = uids.size();
                List<String> uidList = new ArrayList<String>(uids);
                if (uidList.size() >= (pageNumber * pageSize)) {
                    uidList = uidList.subList(((pageNumber-1) * pageSize), (pageNumber * pageSize));
                } else if(uidList.size() < (pageNumber * pageSize) && uidList.size() > ((pageNumber-1) * pageSize)) {
                    uidList = uidList.subList(((pageNumber-1) * pageSize), uidList.size());
                } else {
                    uidList.clear();
                }

                if (uidList.size() > 0) {
                    Set<String> uidSet = new HashSet<>(uidList);
                    if ("0".equals(to)) { //to=0 POI
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.POI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    } else { //to=1 AOI
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.AOI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    }
                } else {
                    result.put(STATUS, 4);
                    result.put(MSG, "未找到AOI、POI信息！");
                    return result;
                }
                PageInfo<Map<String, String>> pageInfo = new PageInfo<>(datas, pageNumber, pageSize, total);
                result.put(STATUS, 0);
                result.put(RESULT, pageInfo);
            }
            else { // 名称不为空
                PageInfo<Map<String, String>> pageInfo;
                if ("0".equals(to)) { //to=0 POI
                    Page<POIInfoEntity> poiInfoEntities;
                    if (StringUtils.isBlank(label)) { //标签为空
                        POIInfoEntity param = new POIInfoEntity();
                        param.setType("0");
                        param.setPoiName(name);
                        param.setProvinceId(province);
                        param.setCityId(city);
                        param.setDelFlag("0");
                        poiInfoEntities = poiService.findByPage(param, pageNumber, pageSize);
                    }
                    else { //标签不为空
                        AreaLabelEntity areaPoiLable = areaLabelService.findById(label);
                        if (areaPoiLable == null) {
                            result.put(STATUS, 24);
                            result.put(MSG, "标签非法");
                            return result;
                        }
                        List<String> labels = areaLabelService.getLabels(areaPoiLable);
                        Map<String, Object> param = new HashMap<>();
                        param.put("type", "0");
                        param.put("poiName", name);
                        param.put("provinceId", province);
                        param.put("cityId", city);
                        param.put("delFlag", "0");
                        param.put("labels", labels);
                        poiInfoEntities = poiService.findByPage(param, pageNumber, pageSize);
                    }

                    for (int i=0; i<poiInfoEntities.size(); i++) {
                        Map<String, String> data = new HashMap<>();
                        data.put(PoiAoiConstant.CITY_ID, poiInfoEntities.get(i).getCityId());
                        data.put(PoiAoiConstant.LNG, poiInfoEntities.get(i).getLngCenter()+"");
                        data.put(PoiAoiConstant.LAT, poiInfoEntities.get(i).getLatCenter()+"");
                        data.put(PoiAoiConstant.CATEGORY, poiInfoEntities.get(i).getCustomLabel());
                        data.put(PoiAoiConstant.NAME, poiInfoEntities.get(i).getPoiName());
                        data.put(PoiAoiConstant.ADDRESS, poiInfoEntities.get(i).getAddress());
                        datas.add(data);
                    }
                    pageInfo = new PageInfo<>(datas, poiInfoEntities);
                } else {
                    Page<AOIInfoEntity> aoiInfoEntities;
                    if (StringUtils.isBlank(label)) { //标签为空
                        AOIInfoEntity param = new AOIInfoEntity();
                        param.setAoiName(name);
                        param.setProvinceId(province);
                        param.setCityId(city);
                        param.setDelFlag("0");
                        aoiInfoEntities = aoiService.findByPage(param, pageNumber, pageSize);
                    }
                    else { //标签不为空
                        AreaLabelEntity areaPoiLable = areaLabelService.findById(label);
                        if (areaPoiLable == null) {
                            result.put(STATUS, 24);
                            result.put(MSG, "标签非法");
                            return result;
                        }
                        List<String> labels = areaLabelService.getLabels(areaPoiLable);
                        Map<String, Object> param = new HashMap<>();
                        param.put("aoiName", name);
                        param.put("provinceId", province);
                        param.put("cityId", city);
                        param.put("delFlag", "0");
                        param.put("labels", labels);
                        aoiInfoEntities = aoiService.findByPage(param, pageNumber, pageSize);
                    }

                    for (int i=0; i<aoiInfoEntities.size(); i++) {
                        Map<String, String> data = new HashMap<>();
                        data.put(PoiAoiConstant.CITY_ID, aoiInfoEntities.get(i).getCityId());
                        data.put(PoiAoiConstant.LNGLAT, aoiInfoEntities.get(i).getRegionAoi());
                        data.put(PoiAoiConstant.CATEGORY, aoiInfoEntities.get(i).getCustomLabel());
                        data.put(PoiAoiConstant.NAME, aoiInfoEntities.get(i).getAoiName());
                        data.put(PoiAoiConstant.ADDRESS, aoiInfoEntities.get(i).getAddress());
                        datas.add(data);
                    }
                    pageInfo = new PageInfo<>(datas, aoiInfoEntities);
                }
                if (pageInfo.getTotal() > 0) {
                    result.put(STATUS, 0);
                    result.put(RESULT, pageInfo);
                } else {
                    result.put(STATUS, 4);
                    result.put(MSG, "未找到AOI、POI信息！");
                    return result;
                }
            }
        } catch (Exception e) {
            result.put(STATUS, 1);
            result.put(MSG, "内部错误");
        }
        return result;
    }

    /**
     * 需求二：根据经纬度查找对应AOI POI（支持标签条件）
     * 参数：
     *      coord 经纬度坐标
     *      label 标签
     *      page 当前页码
     *      size 每页的数量
     */
    @RequestMapping(value = "/fun2", method = RequestMethod.GET)
    public JSONObject searchByCoordinate(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 获取参数
        String coord = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "coord", ""));
        String label = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "label", ""));
        int pageNumber = 1;
        int pageSize = 20;
        long total = 0;
        try {
            pageNumber = ServletRequestUtils.getIntParameter(request, "page", 1);
            pageSize = ServletRequestUtils.getIntParameter(request, "size", 20);
        } catch (Exception e) {
            result.put(STATUS, 11);
            result.put(MSG, "page或size参数非法");
            return result;
        }
        // 判断参数
        if (!coord.matches("[0-9]*(\\.?)[0-9]*,[0-9]*(\\.?)[0-9]*")) {
            result.put(STATUS, 31);
            result.put(MSG, "经纬度非法");
            return result;
        }
        // 搜索
        try {
            // 处理经纬度参数
            String lng = CommonMethod.retainDigits(Double.valueOf(coord.split(PoiAoiConstant.SPLIT_COMMA)[0]));
            String lat = CommonMethod.retainDigits(Double.valueOf(coord.split(PoiAoiConstant.SPLIT_COMMA)[1]));

            List<Map<String, String>> datas = new ArrayList<>();
            String poiGridKey;
            String aoiGridKey;
            Set<String> poiUids = new HashSet<>();
            Set<String> aoiUids = new HashSet<>();
            Set<String> containPoiUids = new HashSet<>();
            Set<String> containAoiUids = new HashSet<>();

            aoiGridKey = PoiAoiConstant.AOI_GRID_PRE + PoiAoiConstant.SEPARATOR_R + lng + PoiAoiConstant.SEPARATOR_R + lat;
            aoiUids = poiAoiRedisService.getUidsByGrid(aoiGridKey);
            if (StringUtils.isBlank(label)) { // 无标签
                // 如果没有AOI进行POI查找
                if (aoiUids.size() == 0) {
                    poiGridKey = PoiAoiConstant.POI_GRID_PRE + PoiAoiConstant.SEPARATOR_R + lng + PoiAoiConstant.SEPARATOR_R + lat;
                    poiUids = poiAoiRedisService.getUidsByGrid(poiGridKey);
                }
                // 根据uid获取POI AOI基本信息
                if (aoiUids.size() > 0) {
                    total = aoiUids.size();
                    List<String> uidList = new ArrayList<>(aoiUids);
                    if (uidList.size() >= (pageNumber * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), (pageNumber * pageSize));
                    } else if(uidList.size() < (pageNumber * pageSize) && uidList.size() > ((pageNumber-1) * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), uidList.size());
                    } else {
                        uidList.clear();
                    }
                    if (uidList.size() > 0) {
                        Set<String> uidSet = new HashSet<>(uidList);
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.AOI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    } else {
                        result.put(STATUS, 4);
                        result.put(MSG, "未找到AOI、POI信息！");
                        return result;
                    }
                } else if (poiUids.size() > 0) {
                    total = poiUids.size();
                    List<String> uidList = new ArrayList<>(poiUids);
                    if (uidList.size() >= (pageNumber * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), (pageNumber * pageSize));
                    } else if(uidList.size() < (pageNumber * pageSize) && uidList.size() > ((pageNumber-1) * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), uidList.size());
                    } else {
                        uidList.clear();
                    }
                    if (uidList.size() > 0) {
                        Set<String> uidSet = new HashSet<>(uidList);
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.POI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    } else {
                        result.put(STATUS, 4);
                        result.put(MSG, "未找到AOI、POI信息！");
                        return result;
                    }
                } else {
                    result.put(STATUS, 4);
                    result.put(MSG, "未找到AOI、POI信息！");
                    return result;
                }
            }
            else { // 有标签
                AreaLabelEntity areaPoiLable = areaLabelService.findById(label);
                if (areaPoiLable == null) {
                    result.put(STATUS, 24);
                    result.put(MSG, "标签非法");
                    return result;
                }
                List<String> labels = areaLabelService.getLabels(areaPoiLable);
                if (aoiUids.size() > 0) {
                    for (String labelSingle : labels) {
                        String labelKey = PoiAoiConstant.AOI_CATEGORY_PRE + PoiAoiConstant.SEPARATOR_R + labelSingle;
                        containAoiUids = poiAoiRedisService.fitLabel(labelKey, aoiUids, containAoiUids, PoiAoiConstant.COUNT_FIT_UID_GRID);
                    }
                }
                if (containAoiUids.size() == 0) {
                    poiGridKey = PoiAoiConstant.POI_GRID_PRE + PoiAoiConstant.SEPARATOR_R + lng + PoiAoiConstant.SEPARATOR_R + lat;
                    poiUids = poiAoiRedisService.getUidsByGrid(poiGridKey);
                    if (poiUids.size() > 0) {
                        for (String labelSingle : labels) {
                            String labelKey = PoiAoiConstant.POI_CATEGORY_PRE + PoiAoiConstant.SEPARATOR_R + labelSingle;
                            containPoiUids = poiAoiRedisService.fitLabel(labelKey, poiUids, containPoiUids, PoiAoiConstant.COUNT_FIT_UID_GRID);
                        }
                    }
                }
                // 根据uid获取POI AOI基本信息
                List<String> uidList = new ArrayList<String>();
                if (containAoiUids.size() > 0) {
                    total = containAoiUids.size();
                    uidList = new ArrayList<String>(containAoiUids);
                    if (uidList.size() >= (pageNumber * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), (pageNumber * pageSize));
                    } else if(uidList.size() < (pageNumber * pageSize) && uidList.size() > ((pageNumber-1) * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), uidList.size());
                    } else {
                        uidList.clear();
                    }
                    if (uidList.size() > 0) {
                        Set<String> uidSet = new HashSet<>(uidList);
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.AOI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    } else {
                        result.put(STATUS, 4);
                        result.put(MSG, "未找到AOI、POI信息！");
                        return result;
                    }
                } else if (containPoiUids.size() > 0) {
                    total = containPoiUids.size();
                    uidList = new ArrayList<String>(containPoiUids);
                    if (uidList.size() >= (pageNumber * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), (pageNumber * pageSize));
                    } else if(uidList.size() < (pageNumber * pageSize) && uidList.size() > ((pageNumber-1) * pageSize)) {
                        uidList = uidList.subList(((pageNumber-1) * pageSize), uidList.size());
                    } else {
                        uidList.clear();
                    }
                    if (uidList.size() > 0) {
                        Set<String> uidSet = new HashSet<>(uidList);
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.POI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    } else {
                        result.put(STATUS, 4);
                        result.put(MSG, "未找到AOI、POI信息！");
                        return result;
                    }
                } else {
                    result.put(STATUS, 4);
                    result.put(MSG, "未找到AOI、POI信息！");
                    return result;
                }
            }

            PageInfo<Map<String, String>> pageInfo = new PageInfo<>(datas, pageNumber, pageSize, total);
            result.put(STATUS, 0);
            result.put(RESULT, pageInfo);
        } catch (Exception e) {
            result.put(STATUS, 1);
            result.put(MSG, "内部错误");
        }

        return result;
    }

    /**
     * 需求三：根据经纬度范围查找对应POI AOI（支持标签条件）
     */
    @RequestMapping(value = "/fun3", method = RequestMethod.GET)
    public JSONObject searchByCoordinateRange(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 获取参数
        String rectangle = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "rectangle", ""));
        String center = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "center", ""));
        String radius = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "radius", ""));
        String label = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "label", ""));
        String to = UTF8Util.convertUtf8(ServletRequestUtils.getStringParameter(request, "to", "0")); // 0:POI;1:AOI(默认to=0)
        int pageNumber = 1;
        int pageSize = 20;
        long total = 0;
        try {
            pageNumber = ServletRequestUtils.getIntParameter(request, "page", 1);
            pageSize = ServletRequestUtils.getIntParameter(request, "size", 20);
        } catch (Exception e) {
            result.put(STATUS, 11);
            result.put(MSG, "page或size参数非法");
            return result;
        }
        // 判断参数
        if (StringUtils.isBlank(rectangle) && StringUtils.isBlank(center)) { // 矩形和圆心参数都为空
            result.put(STATUS, 36);
            result.put(MSG, "范围参数非法");
            return result;
        }
        if (StringUtils.isNotBlank(rectangle) && StringUtils.isNotBlank(center)) { // 矩形和圆心参数都不为空
            result.put(STATUS, 36);
            result.put(MSG, "范围参数非法");
            return result;
        }
        if (!"0".equals(to) && !"1".equals(to)) {
            result.put(STATUS, 22);
            result.put(MSG, "to非法");
            return result;
        }
        try {
            // 处理经纬度范围参数
            String range;
            Set<String> gridKeys = new HashSet<>();
            if (StringUtils.isNotBlank(rectangle)) { // 矩形范围
                if (!poiAoiBaseService.fitRectangleFormat(rectangle)) {
                    result.put(STATUS, 37);
                    result.put(MSG, "矩形参数非法");
                    return result;
                }
                range = rectangle;
                if ("0".equals(to)) { // 搜索POI
                    gridKeys = poiAoiBaseService.getGridKeys(PoiAoiConstant.POI_GRID_PRE, range);
                } else { // 搜索AOI
                    gridKeys = poiAoiBaseService.getGridKeys(PoiAoiConstant.AOI_GRID_PRE, range);
                }
            } else {
                if (!poiAoiBaseService.fitCircleFormat(center, radius)) {
                    result.put(STATUS, 38);
                    result.put(MSG, "圆形参数非法");
                    return result;
                }
                range = poiAoiBaseService.getRangeByCircle(center, radius);
                if ("0".equals(to)) { // 搜索POI
                    gridKeys = poiAoiBaseService.getGridKeysCircle(PoiAoiConstant.POI_GRID_PRE, range, center, radius);
                } else { // 搜索AOI
                    gridKeys = poiAoiBaseService.getGridKeysCircle(PoiAoiConstant.AOI_GRID_PRE, range, center, radius);
                }
            }
            // 搜索
            List<Map<String, String>> datas = new ArrayList<>();
            Set<String> uids = new HashSet<>();
            Set<String> containUids = new HashSet<>();
            uids = poiAoiRedisService.getUidsByGrid(gridKeys, PoiAoiConstant.COUNT_SEARCH_GRID, uids);
            if ("0".equals(to)) { // 搜索POI
                if (StringUtils.isNotBlank(label)) { // 有标签
                    AreaLabelEntity areaPoiLable = areaLabelService.findById(label);
                    if (areaPoiLable == null) {
                        result.put(STATUS, 24);
                        result.put(MSG, "标签非法");
                        return result;
                    }
                    List<String> labels = areaLabelService.getLabels(areaPoiLable);
                    String labelKey;
                    for (String labelSingle : labels) {
                        labelKey = PoiAoiConstant.POI_CATEGORY_PRE + PoiAoiConstant.SEPARATOR_R + labelSingle;
                        containUids = poiAoiRedisService.fitLabel(labelKey, uids, containUids, PoiAoiConstant.COUNT_FIT_UID_GRID);
                    }
                } else {
                    containUids = uids;
                }
            } else { // 搜索AOI
                if (StringUtils.isNotBlank(label)) { // 有标签
                    AreaLabelEntity areaPoiLable = areaLabelService.findById(label);
                    if (areaPoiLable == null) {
                        result.put(STATUS, 24);
                        result.put(MSG, "标签非法");
                        return result;
                    }
                    List<String> labels = areaLabelService.getLabels(areaPoiLable);
                    String labelKey;
                    for (String labelSingle : labels) {
                        labelKey = PoiAoiConstant.AOI_CATEGORY_PRE + PoiAoiConstant.SEPARATOR_R + labelSingle;
                        containUids = poiAoiRedisService.fitLabel(labelKey, uids, containUids, PoiAoiConstant.COUNT_FIT_UID_GRID);
                    }
                } else {
                    containUids = uids;
                }
            }
            // 根据uid获得data
            if (containUids.size() > 0) {
                total = containUids.size();
                List<String> uidList = new ArrayList<String>(containUids);
                if (uidList.size() >= (pageNumber * pageSize)) {
                    uidList = uidList.subList(((pageNumber-1) * pageSize), (pageNumber * pageSize));
                } else if(uidList.size() < (pageNumber * pageSize) && uidList.size() > ((pageNumber-1) * pageSize)) {
                    uidList = uidList.subList(((pageNumber-1) * pageSize), uidList.size());
                } else {
                    uidList.clear();
                }
                if (uidList.size() > 0) {
                    Set<String> uidSet = new HashSet<>(uidList);
                    if ("0".equals(to)) { //to=0 POI
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.POI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    } else { //to=1 AOI
                        datas = poiAoiRedisService.getDataByUid(PoiAoiConstant.AOI_PRE, uidSet, PoiAoiConstant.COUNT_SEARCH_UID);
                    }
                } else {
                    result.put(STATUS, 4);
                    result.put(MSG, "未找到AOI、POI信息！");
                    return result;
                }
            } else {
                result.put(STATUS, 4);
                result.put(MSG, "未找到POI信息！");
                return result;
            }

            PageInfo<Map<String, String>> pageInfo = new PageInfo<>(datas, pageNumber, pageSize, total);
            result.put(STATUS, 0);
            result.put(RESULT, pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.put(STATUS, 1);
            result.put(MSG, "内部错误");
        }
        return result;
    }
}
