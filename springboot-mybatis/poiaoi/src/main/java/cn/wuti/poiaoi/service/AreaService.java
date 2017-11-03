package cn.wuti.poiaoi.service;

import cn.wuti.poiaoi.entity.AreaEntity;
import cn.wuti.poiaoi.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域Service
 * Created by Administrator on 2017/11/2.
 */
@Service(value = "areaService")
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    public List<AreaEntity> getAll() {
        return areaMapper.getAll();
    }

    public AreaEntity getOne(String id) {
        return areaMapper.getOne(id);
    }

    public void insert(AreaEntity areaEntity) {
        areaMapper.insert(areaEntity);
    }

    public void update(AreaEntity areaEntity) {
        areaMapper.update(areaEntity);
    }

    public void delete(String id) {
        areaMapper.delete(id);
    }

}
