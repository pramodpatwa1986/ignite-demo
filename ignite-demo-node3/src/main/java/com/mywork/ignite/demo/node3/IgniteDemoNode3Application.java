package com.mywork.ignite.demo.node3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mywork.ignite.demo")
public class IgniteDemoNode3Application {

	public static void main(String[] args) {
		SpringApplication.run(IgniteDemoNode3Application.class, args);
	}

}
