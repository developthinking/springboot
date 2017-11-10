package com.wt.lbsWeb.dao;

import com.wt.lbsWeb.base.dao.BaseDao;
import com.wt.lbsWeb.entity.AreaRegionEntity;

/**
 * 区域Dao
 * Created by Administrator on 2017/11/8.
 */
public interface AreaRegionDao extends BaseDao<AreaRegionEntity> {

    String getParentidById(String id);
}
