package com.wt.lbsWeb.dao;

import com.github.pagehelper.Page;
import com.wt.lbsWeb.entity.AreaRegionEntity;

import java.util.List;

/**
 * 区域Dao
 * Created by Administrator on 2017/11/8.
 */
public interface AreaRegionDao {

    List<AreaRegionEntity> findAll();

    Page<AreaRegionEntity> findByPage();
}
