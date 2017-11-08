package com.wt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.wt.lbsWeb.dao")
public class LbsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LbsWebApplication.class, args);
	}
}
