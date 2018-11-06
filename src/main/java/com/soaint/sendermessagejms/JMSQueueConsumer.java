/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soaint.sendermessagejms;

import com.soaint.sendermessagejms.util.ContextUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 *
 * @author ELITEBOOK
 */
public class JMSQueueConsumer implements MessageListener {

    private final QueueConnectionFactory queueConnectionFactory;
    private final QueueConnection queueConnection;
    private final QueueSession queueSession;
    private final Queue queue;
    private final QueueReceiver queueReceiver;
    private TextMessage textMessage;
    private boolean quit;

    public JMSQueueConsumer(Context context, String queueName) throws NamingException, JMSException {
        this.queueConnectionFactory = (QueueConnectionFactory) context
                .lookup(ContextUtil.JMS_FACTORY);
        this.queueConnection = queueConnectionFactory.createQueueConnection();
        this.queueSession = queueConnection.createQueueSession(false,
                Session.AUTO_ACKNOWLEDGE);
        this.queue = (Queue) context.lookup(queueName);
        this.textMessage = queueSession.createTextMessage();
        this.queueReceiver = queueSession.createReceiver(queue);
        this.queueReceiver.setMessageListener(this);
        this.quit = false;
    }

    public void start() throws JMSException {
        queueConnection.start();
    }

    public void close() throws JMSException {
        queueSession.close();
        queueConnection.close();
    }

    @Override
    public void onMessage(Message msg) {
        String consumedMessagefromQueue = null;
 
        try {
            if (msg instanceof TextMessage) { // It's TextMessage...
 
                textMessage = (TextMessage) msg;
                consumedMessagefromQueue = textMessage.getText();
            }
            else { // If it is not a TextMessage...
 
                consumedMessagefromQueue = msg.toString();
            }
 
            // finally print the message to the output console
            System.out.println("JMSQueueConsumer: Message consumed from JMS Queue 'TestQueue' is >>>" + consumedMessagefromQueue + "<<<");
 
            if (consumedMessagefromQueue.equalsIgnoreCase("quit")) {
                synchronized(this) {
                    quit = true;
                    this.notifyAll(); // Notify main thread to quit
                }
            }
        } 
        catch (JMSException jmsex) {
            Logger.getLogger(JMSQueueConsumer.class.getName()).log(Level.SEVERE, null, jmsex);
        }
    }
    
    public boolean isQuit(){
        return quit;
    }
}
