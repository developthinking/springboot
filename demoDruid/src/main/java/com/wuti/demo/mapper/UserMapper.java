package com.wuti.demo.mapper;

import java.util.List;

import com.wuti.demo.entity.UserEntity;
import com.wuti.demo.param.UserParam;

public interface UserMapper {
	
	List<UserEntity> getAll();
	
	List<UserEntity> getList(UserParam userParam);
	
	int getCount(UserParam userParam);
	
	UserEntity getOne(Long id);
	
	void insert(UserEntity user);
	
	int update(UserEntity user);
	
	int delete(Long id);

}
