package com.wt.lbsWeb.service;

import com.wt.lbsWeb.base.service.BaseService;
import com.wt.lbsWeb.dao.AreaRegionDao;
import com.wt.lbsWeb.entity.AreaRegionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service(value = "areaRegionService")
public class AreaRegionService extends BaseService<AreaRegionEntity> {

    @Autowired
    private AreaRegionDao areaRegionDao;

    @Override
    public AreaRegionDao getDao() {
        return areaRegionDao;
    }

    /**
     * 判断省市ID关系是否正确
     * @param province 省id
     * @param city 市id
     */
    public boolean fitProvinceCity(String province, String city) {
        if (StringUtils.isBlank(province) || StringUtils.isBlank(city)) {
            return false;
        }
        if ("131".equals(province) || "132".equals(province) || "289".equals(province) || "332".equals(province) || "2911".equals(province) || "2912".equals(province)) { //北京、重庆、上海、天津、澳门、香港
            if (province.equals(city)) {
                return true;
            } else {
                return false;
            }
        } else {
            String parentId = areaRegionDao.getParentidById(city);
            if (province.equals(parentId)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
