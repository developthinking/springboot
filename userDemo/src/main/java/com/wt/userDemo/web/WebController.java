package com.wt.userDemo.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wt.userDemo.domain.User;

@RestController
public class WebController {
	
	@RequestMapping("/getUser")
	public User getUser() {
		User user = new User();
		user.setUserName("小明");
		user.setAge(12);
		user.setPassword("123456");
		return user;
	}
	
	@RequestMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users=new ArrayList<User>();
        User user1=new User();
        user1.setUserName("neo");
        user1.setAge(30);
        user1.setPassword("neo123");
        users.add(user1);
        User user2=new User();
        user2.setUserName("小明");
        user2.setAge(12);
        user2.setPassword("123456");
        users.add(user2);
       return users;
    }
	
	@RequestMapping("/saveUser")
	public void saveUser(@Valid User user, BindingResult result) {
		System.out.println("user:"+user);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
	        for (ObjectError error : list) {
	            System.out.println(error.getCode() + "-" + error.getDefaultMessage());
	        }
		}
	}
	
}
