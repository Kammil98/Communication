package com.learning.messaging.service.Receiver;

public interface MessageReceiver {

    /**
     * Receive message with reply
     *
     * @param message message which should be received
     * @return message which is replied
     */
    String receiveAndReply(final String message);

    /**
     * Receive message without reply
     *
     * @param message message which should be received
     */
    void receive(final String message);
}
