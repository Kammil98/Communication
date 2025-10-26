package com.learning.messaging.service.Sender;

import java.util.HashMap;

public interface MessageSender {
    /**
     * Send message
     *
     * @param config eventual configuration for sender which might be needed. F.e. REST sender requires url
     * @param message message which should be sent
     * @return true if message was sent successfully, false otherwise
     */
    Boolean send(HashMap<String, Object> config, String message);
}
