package com.jensonjo.sqsjmsconsumer;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.jensonjo.sqsjmsconsumer.listener.MyListener;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class SqsJmsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsJmsConsumerApplication.class, args);

//        log.info("Create a new connection factory with all defaults (credentials and region) set automatically");
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.defaultClient()
        );

        try {
//            log.info("Create the connection");
            SQSConnection connection = connectionFactory.createConnection();

//            log.info("Create the nontransacted session with AUTO_ACKNOWLEDGE mode");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//            log.info("Create a queue identity and specify the queue name to the session");
            Queue queue = session.createQueue("MyQueue");

//            log.info("Create a consumer for the 'MyQueue'.");
            MessageConsumer consumer = session.createConsumer(queue);

            log.info("Instantiate and set the message listener for the consumer.");
            consumer.setMessageListener(new MyListener());

            connection.start();

            Thread.sleep(2000);

        } catch (JMSException e) {
            log.severe(e.getLocalizedMessage());
        } catch (InterruptedException e) {
            log.severe(e.getLocalizedMessage());
        }

    }
}
