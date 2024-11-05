package com.fiveshop.fiveshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@MapperScan("com.fiveshop.fiveshop.mapper") // 指定 Mapper 接口的包路径
@SpringBootApplication
@Slf4j
public class FiveshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiveshopApplication.class, args);
		log.info("項目已啟動...");
	}

}
