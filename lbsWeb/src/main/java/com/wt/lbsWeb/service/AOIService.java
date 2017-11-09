package com.wt.lbsWeb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wt.lbsWeb.base.service.BaseService;
import com.wt.lbsWeb.dao.AOIDao;
import com.wt.lbsWeb.entity.AOIInfoEntity;
import com.wt.lbsWeb.entity.POIInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AOI实现类
 * Created by Administrator on 2017/11/9.
 */
@Service(value = "aoiService")
public class AOIService extends BaseService<AOIInfoEntity> {

    @Autowired
    private AOIDao aoiDao;

    @Override
    public AOIDao getDao() {
        return aoiDao;
    }

    public Page<AOIInfoEntity> findByLabelsPage(Object param, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return aoiDao.findByLabelsPage(param);
    }
}
