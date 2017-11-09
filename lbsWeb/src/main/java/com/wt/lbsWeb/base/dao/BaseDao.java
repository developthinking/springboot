package com.wt.lbsWeb.base.dao;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * BaseDao
 * Created by Administrator on 2017/11/9.
 */
public interface BaseDao<T> {

    public T findById(Object id);

    public List<T> findAll(Object obj);

    public Page<T> findByPage(Object obj);
}
