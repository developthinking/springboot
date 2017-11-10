package com.wt.lbsWeb.dao;

import com.wt.lbsWeb.base.dao.BaseDao;
import com.wt.lbsWeb.entity.AreaLabelEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */
public interface AreaLabelDao extends BaseDao<AreaLabelEntity> {

    List<String> getSecondClassLabelsByFirstClassId(String parentId);

    List<String> getThirdClassLabelsByFirstClassId(String parentId);

    List<String> getThirdClassLabelsBySecondClassId(String parentId);
}
