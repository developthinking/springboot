package com.wt.lbsWeb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wt.lbsWeb.dao.AreaRegionDao;
import com.wt.lbsWeb.entity.AreaRegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service(value = "areaRegionService")
public class AreaRegionService {

    @Autowired
    private AreaRegionDao areaRegionDao;

    public List<AreaRegionEntity> listAll() {
        return areaRegionDao.findAll();
    }

    public Page<AreaRegionEntity> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return areaRegionDao.findByPage();
    }
}
