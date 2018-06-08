package com.wuti.demo.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.wuti.demo.param.PageParam;

/**
 * 功能描述：分页封装数据  
 * @author: WU
 * @date: 2018年6月8日 上午10:00:56 
 *
 */
public class Page<E> implements Serializable {
	
	private static final long serialVersionUID = -2020350783443768083L;
	
	private int currentPage = 1;  // 当前页数
    private long totalPage;       // 总页数
    private long totalNumber;     // 总记录数
    private List<E> list;         // 数据集
    
    public static Page NULL = new Page(0, 0, 15, new ArrayList<>());
    
	public Page() {
		super();
	}
	
    public Page(int beginLine, long totalNumber, int pageSize, List<E> list) {
		super();
		this.currentPage = beginLine / pageSize + 1;
		this.totalNumber = totalNumber;
		this.list = list;
		this.totalPage = totalNumber % pageSize == 0 ? (totalNumber / pageSize) : (totalNumber / pageSize + 1);
	}

    public Page(PageParam pageParam, long totalNumber, List<E> list) {
        super();
        this.currentPage = pageParam.getCurrentPage();
        this.totalNumber = totalNumber;
        this.list = list;
        this.totalPage = totalNumber % pageParam.getPageSize() == 0 ? (totalNumber / pageParam.getPageSize()) : (totalNumber / pageParam.getPageSize() + 1);
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	
}
