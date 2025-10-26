package com.learning.messaging.service.Sender.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.messaging.model.Person;
import com.learning.messaging.service.Sender.MessageSender;
import com.learning.messaging.service.Sender.PersonSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class RESTSender implements MessageSender, PersonSender {

    private final Logger logger = LoggerFactory.getLogger(RESTSender.class);

    @Override
    public Boolean send(HashMap<String, Object> config, String message) {
        HttpURLConnection con = null;
        try {
            // prepare request
            final URL url;
            if (config.get("url") instanceof String configUrl) {
                url = new URI(configUrl + getParamsString(Map.of("message", message))).toURL();
            } else {
                throw new InvalidUrlException(String.format("url for message %s is not correct", message));
            }

            //header
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("charset", "utf-8");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            //logging
            logger.info("[{}]: {}", con.getRequestMethod(), url);
            logger.info("REST message {} sent with message '{}'", url, message);
            logger.info("Response code for {} is {}", url, con.getResponseCode());
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("Could not send request with message '{}'", message);
        } finally {
            if(con != null) {
                con.disconnect();
            }
        }
        return false;
    }

    @Override
    public Boolean send(HashMap<String, Object> config, Person person) {
        HttpURLConnection con = null;
        try {
            // prepare request
            URL url;
            if (config.get("url") instanceof String configUrl) {
                url = new URI(configUrl).toURL();
            } else {
                throw new InvalidUrlException(String.format("url for person %s is not correct", person));
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("charset", "utf-8");
            //send application/json
            con.setRequestProperty("Content-Type", "application/json");
            //expect application/json as response
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);

            addBody(con, person);
            //logging
            logger.info("[{}]: {}", con.getRequestMethod(), url);
            logger.info("REST request {} sent with person {}", url, person);
            logger.info("Response code for {} is {}", url, con.getResponseCode());
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("Could not send request with person {}", person);
        } finally {
            if(con != null) {
                con.disconnect();
            }
        }
        return false;
    }

    private void addBody(HttpURLConnection con, Person person) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
            byte[] bytes = objectMapper.writeValueAsBytes(person);
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        if (!params.isEmpty()) {
            result.append("?");
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
