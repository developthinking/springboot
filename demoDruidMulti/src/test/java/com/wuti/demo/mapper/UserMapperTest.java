package com.wuti.demo.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wuti.demo.entity.UserEntity;
import com.wuti.demo.enums.UserSexEnum;
import com.wuti.demo.mapper.one.UserOneMapper;
import com.wuti.demo.mapper.two.UserTwoMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
	
	@Resource
	private UserOneMapper userOneMapper;
	@Resource
	private UserTwoMapper userTwoMapper;
	
	/*@Test
	public void testInsert() throws Exception {
	    userOneMapper.insert(new UserEntity("mmm", "m123456", UserSexEnum.MAN));
	    userOneMapper.insert(new UserEntity("nnn", "n123456", UserSexEnum.WOMAN));
	    userTwoMapper.insert(new UserEntity("aaa", "a123456", UserSexEnum.WOMAN));
	}*/
	
	/*@Test
	public void testQuery() {
		List<UserEntity> users = userOneMapper.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.toString());
		}
	}*/
	
	@Test
	public void testUpdate() {
		long id = 31;
		UserEntity user = userOneMapper.getOne(id);
		if(user != null){
			System.out.println(user.toString());
			user.setNickName("阿题");
			userOneMapper.update(user);
		} else {
			System.out.println("not find user id="+id);
		}
	}
	
}
