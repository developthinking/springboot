package com.wt.userDemo.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.wt.userDemo.web.WebController;

@SpringBootTest
public class WebControllerTest {
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new WebController()).build();
	}

//	@Test
//	public void getUser() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/getUser")).andDo(MockMvcResultHandlers.print());
//	}
	
	@Test
	public void saveUsers() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.post("/saveUser")
	            .param("name","")
	            .param("age","666")
	            .param("password","test")
	    ).andDo(MockMvcResultHandlers.print());
	}
	
}
