package com.camunda.fox.showcase.fruitshop.order.process;

import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.BpmnError;

import com.camunda.fox.showcase.fruitshop.inventory.boundary.InventoryService;
import com.camunda.fox.showcase.fruitshop.order.boundary.OrderService;
import com.camunda.fox.showcase.fruitshop.order.entity.Order;
import com.camunda.fox.showcase.fruitshop.payment.boundary.PaymentService;

@Named
public class ProcessOrder {
  
  public static final String VARNAME_ORDER_ID = "orderId";
  
  @Inject
  private InventoryService inventoryService;
  
  @Inject
  private OrderService orderService;
  
  @Inject
  private PaymentService paymentService;
  
  @Inject 
  private BusinessProcess businessProcess;
  
  public void reserveOrderItems() {
    
    Long orderId = businessProcess.getVariable(VARNAME_ORDER_ID);
    boolean orderConfirmed = inventoryService.reserveOrderItems(orderId);
    
    if(!orderConfirmed) {
      throw new BpmnError("itemsOutOfStock");
    }
    
  }
  
  public void releaseOrderItemReservation() {
    
    Long orderId = businessProcess.getVariable(VARNAME_ORDER_ID);
    inventoryService.releaseReservation(orderId);
    
  }
  
  public void status(String newStatus) {
	
	Long orderId = businessProcess.getVariable(VARNAME_ORDER_ID);
	orderService.updateOrderStatus(orderId, newStatus);
  }
  
  public void performPayment() {
	Long orderId = businessProcess.getVariable(VARNAME_ORDER_ID);
	Order order = orderService.getOrder(orderId);
	
	String payment = paymentService.performPayment(order.getTotal(), order.getCustomer());
	
	boolean isPaymentPerformed = "PAYMENT_STATUS_OK".equals(payment);
	businessProcess.setVariable("isPaymentPerformed", isPaymentPerformed);
  }

}
