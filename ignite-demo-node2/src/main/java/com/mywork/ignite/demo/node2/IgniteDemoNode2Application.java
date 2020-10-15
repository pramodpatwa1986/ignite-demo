package com.mywork.ignite.demo.node2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mywork.ignite.demo.node2")
public class IgniteDemoNode2Application {

	public static void main(String[] args) {
		SpringApplication.run(IgniteDemoNode2Application.class, args);
	}

}
