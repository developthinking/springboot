package com.wuti.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wuti.demo.entity.UserEntity;
import com.wuti.demo.mapper.one.UserOneMapper;
import com.wuti.demo.mapper.two.UserTwoMapper;
import com.wuti.demo.param.UserParam;
import com.wuti.demo.result.Page;

@RestController
public class UserController {

	@Autowired
	private UserOneMapper userOneMapper;
	@Autowired
	private UserTwoMapper userTwoMapper;
	
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> usersOne = userOneMapper.getAll();
        List<UserEntity> usersTwo = userTwoMapper.getAll();
        usersOne.addAll(usersTwo);
        return usersOne;
	}
	
	@RequestMapping("/getList")
    public Page<UserEntity> getList(UserParam userParam) {
        List<UserEntity> users = userOneMapper.getList(userParam);
        long count = userOneMapper.getCount(userParam);
        Page page = new Page(userParam,count,users);
        return page;
    }
	
	@RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userTwoMapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(UserEntity user) {
        userOneMapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(UserEntity user) {
        userOneMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userTwoMapper.delete(id);
    }
	
}
