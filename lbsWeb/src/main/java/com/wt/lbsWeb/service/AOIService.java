package com.wt.lbsWeb.service;

import com.wt.lbsWeb.base.service.BaseService;
import com.wt.lbsWeb.dao.AOIDao;
import com.wt.lbsWeb.entity.AOIInfoEntity;
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

}
