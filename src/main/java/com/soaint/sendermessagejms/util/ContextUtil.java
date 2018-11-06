/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soaint.sendermessagejms.util;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ELITEBOOK
 */
public class ContextUtil {
    
    public final static String SERVER = "t3://localhost:7001";
    public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public final static String JMS_FACTORY = "FabricaConexionesJMS";
    public final static String QUEUE = "ColaJMSRegistroIMEI";
    public final static String USER = "weblogic";
    public final static String PASSWORD = "admin123";
    
    
    public static InitialContext getInitialContext() throws NamingException{
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, SERVER);
        env.put(Context.SECURITY_PRINCIPAL, USER);
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        return new InitialContext(env);
    }
}
