package com.jensonjo.sqsjmsconsumer.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import lombok.extern.java.Log;

/**
 * Created by jensonkakkattil on Apr, 2019.
 */
@Log
public class MyListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
//            log.info("Cast the received message as TextMessage and print the test to the screen.");
            log.info("Received : " + ((TextMessage) message).getText());
        } catch (JMSException e) {
            log.severe(e.getLocalizedMessage());
        }

    }
}
