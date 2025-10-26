package com.learning.messaging.service.Sender.impl;

import com.learning.messaging.config.RabbitMQConfiguration;
import com.learning.messaging.model.Person;
import com.learning.messaging.service.Sender.MessageSender;
import com.learning.messaging.service.Sender.PersonSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("RabbitMQSender")
public class RabbitMQSender implements MessageSender, PersonSender {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${messaging.rabbitmq.queue.name}")
    private String defaultQueueName;
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Boolean send(HashMap<String, Object> config, String message) {
        String queueName = getQueName(config);
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.ROUTING_KEY, message);
        } catch (AmqpException e) {
            logger.error("Could not send message '{}'", message);
            return false;
        }
        logger.info("Sent message '{}'", message);
        return true;
    }

    @Override
    public Boolean send(HashMap<String, Object> config, Person person) {
        String queueName = getQueName(config);
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.ROUTING_KEY, person);
        } catch (AmqpException e) {
            logger.error("Could not send message {}", person);
            return false;
        }
        logger.info("Sent message {}", person);
        return true;
    }

    private String getQueName(HashMap<String, Object> config) {
        String queueName = (String) config.get("queueName");
        if (StringUtils.isBlank(queueName)) {
            return defaultQueueName;
        }
        return  queueName;
    }
}
