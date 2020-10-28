package com.mywork.ignite.demo.node1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mywork.ignite.demo")
public class IgniteDemoNode1Application {

	public static void main(String[] args) {
		SpringApplication.run(IgniteDemoNode1Application.class, args);
	}

}
