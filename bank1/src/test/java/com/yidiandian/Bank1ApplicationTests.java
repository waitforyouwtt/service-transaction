package com.yidiandian;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//实现feign 服务间通信
@EnableDiscoveryClient
@EnableFeignClients
//注册到eureka 注册中心
@EnableEurekaClient
@SpringBootTest
class Bank1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
