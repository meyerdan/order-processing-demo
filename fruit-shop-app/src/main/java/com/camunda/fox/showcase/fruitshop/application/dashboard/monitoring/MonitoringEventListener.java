package com.camunda.fox.showcase.fruitshop.application.dashboard.monitoring;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.TransactionManager;

import org.activiti.cdi.impl.util.ProgrammaticBeanLookup;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.camunda.fox.showcase.fruitshop.application.dashboard.boundary.rest.BroadCaster;


public class MonitoringEventListener implements ExecutionListener {

    
  public void notify(DelegateExecution execution) throws Exception {
    
      
      final BroadCaster broadcaster = InitialContext.doLookup("java:module/BroadCaster");
      
      final MonitoringEvent event = new MonitoringEvent();
      event.setActivityId(execution.getCurrentActivityId());
      event.setType(execution.getEventName());
      
      TransactionManager transactionManager = InitialContext.doLookup("java:jboss/TransactionManager");
      transactionManager.getTransaction().registerSynchronization(new Synchronization() {
        
        public void beforeCompletion() {
        }
        
        public void afterCompletion(int status) {
          if(status == Status.STATUS_COMMITTED)  {
            broadcaster.broadcast(event);
          }
        }
      });
    
  }
  
  
}
