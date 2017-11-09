package com.wt.lbsWeb.controller;

import com.github.pagehelper.Page;
import com.wt.lbsWeb.base.page.PageInfo;
import com.wt.lbsWeb.entity.AreaRegionEntity;
import com.wt.lbsWeb.service.AreaRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/8.
 */
@RestController
@RequestMapping(value = "/region")
public class AreaRegionController {

    @Autowired
    private AreaRegionService regionService;

    @RequestMapping(value = "/list")
    public PageInfo<AreaRegionEntity> listAll() {
        try {
            Page<AreaRegionEntity> areaRegionEntities = regionService.findByPage(null, 1, 10);
            PageInfo<AreaRegionEntity> pageInfo = new PageInfo<>(areaRegionEntities);
            return pageInfo;
        } catch (Exception e) {
            return null;
        }
    }
}
