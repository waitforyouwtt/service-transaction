package com.yidiandian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

//实现feign 服务间通信
@EnableDiscoveryClient
@EnableFeignClients
//注册到eureka 注册中心
@EnableEurekaClient
@SpringBootApplication
@EnableHystrix
public class Bank1Application {

	public static void main(String[] args) {
		SpringApplication.run(Bank1Application.class, args);
	}

}
