package com.learning.messaging.service.Receiver;

import com.learning.messaging.model.Person;

public interface PersonReceiver {
    Object receive(final Person person);
}
