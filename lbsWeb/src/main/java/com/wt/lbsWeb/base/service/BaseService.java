package com.wt.lbsWeb.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wt.lbsWeb.base.dao.BaseDao;

import java.util.List;

/**
 * BaseService
 * Created by Administrator on 2017/11/9.
 */
public abstract class BaseService<T> {

    public abstract BaseDao<T> getDao();

    public T findById(Object id) throws Exception {
        return getDao().findById(id);
    }

    public List<T> findAll(Object obj) throws Exception {
        return getDao().findAll(obj);
    }

    public Page<T> findByPage(Object obj, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return getDao().findByPage(obj);
    }
}
