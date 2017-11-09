package com.wt.lbsWeb.dao;

import com.github.pagehelper.Page;
import com.wt.lbsWeb.base.dao.BaseDao;
import com.wt.lbsWeb.entity.AOIInfoEntity;

/**
 * Created by Administrator on 2017/11/9.
 */
public interface AOIDao extends BaseDao<AOIInfoEntity> {

    public Page<AOIInfoEntity> findByLabelsPage(Object obj);
}
