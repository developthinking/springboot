package cn.wuti.poiaoi.mapper;

import cn.wuti.poiaoi.entity.AreaEntity;

import java.util.List;

/**
 * AreaEntity mapper层
 * Created by Administrator on 2017/11/2.
 */
public interface AreaMapper {

    List<AreaEntity> getAll();

    AreaEntity getOne(String id);

    void insert(AreaEntity areaEntity);

    void update(AreaEntity areaEntity);

    void delete(String id);
}
