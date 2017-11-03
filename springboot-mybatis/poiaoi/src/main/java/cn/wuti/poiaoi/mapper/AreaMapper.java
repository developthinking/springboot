package cn.wuti.poiaoi.mapper;

import cn.wuti.poiaoi.entity.AreaEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 区域Mapper
 * Created by Administrator on 2017/11/2.
 */
public interface AreaMapper {

    @Select("SELECT * FROM area_region ORDER BY TO_NUMBER(id)")
    @Results({
            @Result(property = "parentId", column = "parent_id", javaType = String.class),
            @Result(property = "levelType", column = "level_type", javaType = String.class)
    })
    List<AreaEntity> getAll();

    @Select("SELECT * FROM area_region WHERE id=#{id}")
    @Results({
            @Result(property = "parentId", column = "parent_id", javaType = String.class),
            @Result(property = "levelType", column = "level_type", javaType = String.class)
    })
    AreaEntity getOne(String id);

    @Insert("INSERT INTO area_region(id,name,parent_id,level_type) VALUES(#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{parentId,jdbcType=VARCHAR}, #{levelType,jdbcType=VARCHAR})")
    void insert(AreaEntity areaEntity);

    @Update("UPDATE area_region SET name=#{name,jdbcType=VARCHAR},parent_id=#{parentId,jdbcType=VARCHAR},level_type=#{levelType,jdbcType=VARCHAR} WHERE id=#{id,jdbcType=VARCHAR}")
    void update(AreaEntity areaEntity);

    @Delete("DELETE FROM area_region WHERE id=#{id}")
    void delete(String id);
}
