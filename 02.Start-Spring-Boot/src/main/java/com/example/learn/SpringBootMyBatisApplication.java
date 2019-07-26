package com.example.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.learn.db"})
@MapperScan("com.example.learn.db.dao")
public class SpringBootMyBatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMyBatisApplication.class, args);
	}

}
