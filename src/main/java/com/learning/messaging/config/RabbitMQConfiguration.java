package com.learning.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String QUEUE_NAME = "simple-message-queue";
    public static final String EXCHANGE_NAME = "simple-message-exchange";
    public static final String ROUTING_KEY = "routing.simple.messages";

    @Bean
    Queue simpleQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange topicName() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue simpleQueue, TopicExchange topicName) {
        return BindingBuilder.bind(simpleQueue).to(topicName).with(ROUTING_KEY + ".#");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        //this is needed to convert Person object to JSON and back when sending via RabbitMQ
        //Without it, an exception will be thrown because we need to allow for deserialization of Person class
        // f.e. with property spring.rabbitmq.deserialization.trust.all=true
        return new Jackson2JsonMessageConverter();
    }
}
