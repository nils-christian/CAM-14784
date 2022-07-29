package com.example.demo;

import org.camunda.bpm.spring.boot.starter.CamundaBpmAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( exclude = CamundaBpmAutoConfiguration.class )
public class DemoApplication {

	public static void main( final String[] args ) {
		SpringApplication.run( DemoApplication.class, args );
	}

}
