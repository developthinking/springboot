package com.wt.lbsWeb.service;

import com.wt.lbsWeb.base.service.BaseService;
import com.wt.lbsWeb.dao.POIDao;
import com.wt.lbsWeb.entity.POIInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * POI实现类
 * Created by Administrator on 2017/11/9.
 */
@Service(value = "poiService")
public class POIService extends BaseService<POIInfoEntity> {

    @Autowired
    private POIDao poiDao;

    @Override
    public POIDao getDao() {
        return poiDao;
    }

}
