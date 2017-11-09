package com.wt.lbsWeb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    public Page<POIInfoEntity> findByLabelsPage(Object param, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return poiDao.findByLabelsPage(param);
    }
}
