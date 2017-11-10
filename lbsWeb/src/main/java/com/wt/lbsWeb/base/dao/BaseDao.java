package com.wt.lbsWeb.base.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BaseDao
 * Created by Administrator on 2017/11/9.
 */
@Mapper
public interface BaseDao<T> {

    T findById(Object id);

    List<T> findAll(Object obj);

    Page<T> findByPage(Object obj);
}
