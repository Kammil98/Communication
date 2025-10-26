package com.learning.messaging.service.Sender;

import com.learning.messaging.model.Person;

import java.util.HashMap;

public interface PersonSender {

    /**
     * Send message
     *
     * @param config eventual configuration for sender which might be needed. F.e. REST sender requires url
     * @param person person which should be sent as a message
     * @return true if message was sent successfully, false otherwise
     */
    Boolean send(HashMap<String, Object> config, Person person);
}
