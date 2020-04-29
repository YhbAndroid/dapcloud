package com.dap.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DapCloudEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DapCloudEurekaApplication.class, args);
	}

}
