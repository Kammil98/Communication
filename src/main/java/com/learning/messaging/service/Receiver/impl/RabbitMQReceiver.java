package com.learning.messaging.service.Receiver.impl;


import com.learning.messaging.model.Person;
import com.learning.messaging.service.Receiver.MessageReceiver;
import com.learning.messaging.service.Receiver.PersonReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@EnableRabbit
@RabbitListener(queues = "${messaging.rabbitmq.queue.name}")
public class RabbitMQReceiver implements MessageReceiver, PersonReceiver {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @Override
    @RabbitHandler
    public void receive(String message) {
        logger.info("Received message: '{}'", message);
    }

    @Override
    @RabbitHandler
    public void receive(Person person) {
        logger.info("Received message: {}", person);
    }

    @Override
    public String receiveAndReply(String message) {
        throw new UnsupportedOperationException("Operation receiveAndReply is not supported for RabbitMQ yet");
    }

    @Override
    public Person receiveAndReply(Person person) {
        throw new UnsupportedOperationException("Operation receiveAndReply is not supported for RabbitMQ yet");
    }
}
