package com.learning.messaging;

import com.learning.messaging.model.Person;
import com.learning.messaging.service.Sender.impl.RESTSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;

@SpringBootApplication
public class MessagingApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MessagingApplication.class, args);
        sendRESTMessage((RESTSender)context.getBean("RESTSender"));
	}

    public static void sendRESTMessage(RESTSender sender) {
        HashMap<String, Object> config = new HashMap<>();
        config.put("url", "http://localhost:8080/rest-message/get-message");
        // GET request
//        sender.send(config, "Hello REST message :)");
        // POST request
        Person person = new Person("Jan", "Nowak", 18);
        sender.send(config, person);
    }
}
