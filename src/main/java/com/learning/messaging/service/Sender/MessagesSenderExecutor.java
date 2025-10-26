package com.learning.messaging.service.Sender;

import com.learning.messaging.model.Person;
import com.learning.messaging.service.Sender.impl.RESTSender;
import com.learning.messaging.service.Sender.impl.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MessagesSenderExecutor implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(MessagesSenderExecutor.class);

    private final MessageSender messageSender;
    private final PersonSender personSender;
    private final Person person;

    public MessagesSenderExecutor(RabbitMQSender rabbitMQSender) {
        this.messageSender = rabbitMQSender;
        this.personSender = rabbitMQSender;
        this.person = new Person("Jan", "Nowak", 18);
    }


    @Override
    public void run(String... args) throws Exception {
        // GET request
//        sendRESTMessage();
        sendRabbitMQMessage();
    }

    public void sendRESTMessage() {
        validateSender(RESTSender.class);
        HashMap<String, Object> config = new HashMap<>();
        config.put("url", "http://localhost:8080/rest-message/get-message");
        // GET request
//        messageSender.send(config, "Hello REST message :)");
        // POST request
        personSender.send(config, person);
    }

    public void sendRabbitMQMessage() {
        validateSender(RabbitMQSender.class);
        // Send text
//        messageSender.send(new HashMap<>(), "Hello rabbitmq message :)");
        // POST request
        personSender.send(new HashMap<>(), person);
    }

    private void validateSender(Class<?> senderClass) {
        if (!(senderClass.isInstance(messageSender)) || !(senderClass.isInstance(personSender))) {
            throw new IllegalArgumentException(
                    String.format("Invalid sender type provided. Expected class: %s" +
                            "\nProvided messageSender class: %s" +
                            "\nProvided personSender class: %s",
                            senderClass,
                            messageSender.getClass(),
                            personSender.getClass())
            );
        }
    }
}
