package com.rodzik.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerServiceRun {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerServiceRun.class, args);
	}
}
