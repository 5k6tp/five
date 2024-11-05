package com.fivecode.fiveadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan("com.fivecode.fiveadmin.mapper")
@Slf4j
public class FiveadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiveadminApplication.class, args);
		log.info("項目啟動成功...");
	}

}
