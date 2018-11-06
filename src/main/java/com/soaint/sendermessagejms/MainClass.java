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
import javax.naming.NamingException;

/**
 *
 * @author ELITEBOOK
 */
public class MainClass {
    
    
    
    public static void main(String[] args) {
        try {
            JMSQueueConsumer consumeMessage = new JMSQueueConsumer(ContextUtil.getInitialContext(),
                    ContextUtil.QUEUE);
            consumeMessage.start();
            synchronized (consumeMessage) {
                while (!consumeMessage.isQuit()) {
                    try {
                        consumeMessage.wait();
                    } catch (InterruptedException ie) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ie);
                    }
                }
            }
            consumeMessage.close();
        } catch (NamingException | JMSException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
