package com.sap.summit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SummitSrvEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummitSrvEurekaApplication.class, args);
	}
}
