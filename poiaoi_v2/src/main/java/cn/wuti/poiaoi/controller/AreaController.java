package cn.wuti.poiaoi.controller;

import cn.wuti.poiaoi.entity.AreaEntity;
import cn.wuti.poiaoi.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 区域Controller
 * Created by Administrator on 2017/11/2.
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaMapper areaMapper;

    @RequestMapping(value = "/list")
    public List<AreaEntity> listAll() {
        return areaMapper.getAll();
    }

    @RequestMapping(value = "/getOne")
    public AreaEntity getOne(String id) {
        return areaMapper.getOne(id);
    }

    @RequestMapping(value = "/add")
    public void save(AreaEntity areaEntity) {
        areaMapper.insert(areaEntity);
    }

    @RequestMapping(value = "/update")
    public void update(AreaEntity areaEntity) {
        areaMapper.update(areaEntity);
    }

    @RequestMapping(value = "/delete")
    public void delete(String id) {
        areaMapper.delete(id);
    }
}
