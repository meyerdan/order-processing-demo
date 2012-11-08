package com.camunda.fox.showcase.fruitshop.application.util;

import java.util.Arrays;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import com.sun.imageio.plugins.common.I18N;

/**
 *
 * @author nico.rehwaldt
 */
public class TransactionManagerLookup {

  public static String[] TRANSACTION_MANAGER_JNDI_NAMES = new String[] {
    "java:appserver/TransactionManager", 
    "java:jboss/TransactionManager"
  };
  
  public static TransactionManager lookupTransactionManager() {
    for (String name: TRANSACTION_MANAGER_JNDI_NAMES) {
      try {
        return InitialContext.doLookup(name);
      } catch (NamingException e) {
        // retry
      }
    }
    
    throw new RuntimeException(
      "Failed to lookup transaction manager using JNDI names " + 
      Arrays.toString(TRANSACTION_MANAGER_JNDI_NAMES));
  }
}
