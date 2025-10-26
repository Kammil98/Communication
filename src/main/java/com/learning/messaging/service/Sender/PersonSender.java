package com.learning.messaging.service.Sender;

import com.learning.messaging.model.Person;

import java.util.HashMap;

public interface PersonSender {
    Boolean send(HashMap<String, Object> config, Person object);
}
