package com.wt.lbsWeb.dao;

import com.github.pagehelper.Page;
import com.wt.lbsWeb.base.dao.BaseDao;
import com.wt.lbsWeb.entity.POIInfoEntity;

/**
 * POI基本信息Dao
 * Created by Administrator on 2017/11/9.
 */
public interface POIDao extends BaseDao<POIInfoEntity> {

    public Page<POIInfoEntity> findByLabelsPage(Object obj);
}
