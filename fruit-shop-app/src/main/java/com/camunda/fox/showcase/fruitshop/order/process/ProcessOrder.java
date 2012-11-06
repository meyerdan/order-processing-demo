package com.camunda.fox.showcase.fruitshop.order.process;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.BusinessProcess;

import com.camunda.fox.showcase.fruitshop.inventory.boundary.InventoryService;

@Named
public class ProcessOrder {
  
  public static final String VARNAME_ORDER_ID = "orderId";
  
  @Inject
  private InventoryService inventoryService;
  
  @Inject 
  private BusinessProcess businessProcess;
  
  public void reserveOrderItems() {
    
    Long orderId = businessProcess.getVariable(VARNAME_ORDER_ID);
    inventoryService.reserveOrderItems(orderId);
    
  }
  
  public void releaseOrderItemReservation() {
    
    Long orderId = businessProcess.getVariable(VARNAME_ORDER_ID);
    inventoryService.releaseReservation(orderId);
    
  }

}
