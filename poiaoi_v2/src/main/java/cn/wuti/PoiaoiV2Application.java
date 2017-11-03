package cn.wuti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.wuti.poiaoi.mapper")
public class PoiaoiV2Application {

	public static void main(String[] args) {
		SpringApplication.run(PoiaoiV2Application.class, args);
	}
}
