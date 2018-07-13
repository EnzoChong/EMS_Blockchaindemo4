package com.sap.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.sap.ems.multitenancy.DynamicDBInit;

@SpringBootApplication
@SpringBootConfiguration
@Import({DynamicDBInit.class})
@EnableFeignClients
@EnableEurekaClient
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
