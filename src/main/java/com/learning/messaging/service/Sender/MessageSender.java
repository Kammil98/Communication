package com.learning.messaging.service.Sender;

import java.util.HashMap;

public interface MessageSender {
    Boolean send(HashMap<String, Object> config, String message);
}
