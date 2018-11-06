/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soaint.sendermessagejms;

import com.soaint.sendermessagejms.util.ContextUtil;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 *
 * @author ELITEBOOK
 */
public class JMSQueueProducer {

    

    private final QueueConnectionFactory queueConnectionFactory;
    private final QueueConnection queueConnection;
    private final QueueSession queueSession;
    private final QueueSender queueSender;
    private final Queue queue;
    private final TextMessage textMessage;

    public JMSQueueProducer(Context context, String queueName) throws NamingException, JMSException {
        this.queueConnectionFactory = (QueueConnectionFactory) context
                .lookup(ContextUtil.JMS_FACTORY);
        this.queueConnection = queueConnectionFactory.createQueueConnection();
        this.queueSession = queueConnection.createQueueSession(false,
                Session.AUTO_ACKNOWLEDGE);
        this.queue = (Queue) context.lookup(queueName);
        this.queueSender = queueSession.createSender(queue);
        this.textMessage = queueSession.createTextMessage();
    }

    public void start() throws JMSException {
        queueConnection.start();
    }

    public void close() throws JMSException {
        queueSender.close();
        queueSession.close();
        queueConnection.close();
    }

    public void post(String message) throws JMSException {
        textMessage.setText(message);
        queueSender.send(textMessage);
    }

}
