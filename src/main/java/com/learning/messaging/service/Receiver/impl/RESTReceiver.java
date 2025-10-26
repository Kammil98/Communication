package com.learning.messaging.service.Receiver.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.messaging.model.Person;
import com.learning.messaging.service.Receiver.MessageReceiver;
import com.learning.messaging.service.Receiver.PersonReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller()
@RequestMapping("/rest-message")
public class RESTReceiver implements MessageReceiver, PersonReceiver {
    private final Logger logger = LoggerFactory.getLogger(RESTReceiver.class);

    @Override
    @GetMapping("/get-message")
    @ResponseStatus( HttpStatus.OK )
    @ResponseBody
    public String receive(@RequestParam final String message) {
        logger.info("Received message: '{}'", message);
        return message;
    }

    @PostMapping("/get-message")
    @ResponseStatus( HttpStatus.OK )
    @ResponseBody
    public Object receive(@RequestBody(required = true) final Person person) {
        logger.info("Received object");
        ObjectMapper objectMapper = new ObjectMapper();
        logPerson(person);
        return person;
    }

    private void logPerson(Person person) {
        logger.info("Logging person: {}", person.toString());
    }
}
