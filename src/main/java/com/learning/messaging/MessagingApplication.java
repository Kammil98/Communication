package com.learning.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MessagingApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MessagingApplication.class, args);
	}
}
