package com.learning.messaging.service.Receiver;

import com.learning.messaging.model.Person;

public interface PersonReceiver {

    /**
     * Receive message without reply
     *
     * @param person object which should be received
     */
    void receive(final Person person);

    /**
     * Receive message with reply
     *
     * @param person object which should be received
     * @return person object which is replied
     */
    Person receiveAndReply(final Person person);
}
