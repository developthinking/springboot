package com.wt.userDemo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wt.userDemo.comm.WTRoverProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesTest {

	@Resource
	private WTRoverProperties properties;
	
	@Test
	public void testProperties() throws Exception {
		System.out.println("title:"+properties.getTitle());
		System.out.println("description:"+properties.getDescription());
	}
	
}
