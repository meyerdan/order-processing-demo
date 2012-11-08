package com.camunda.fox.showcase.fruitshop.inventory.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.camunda.fox.showcase.fruitshop.inventory.entity.InventoryItem;
import com.camunda.fox.showcase.fruitshop.order.entity.OrderItem;

@Stateless
public class InventoryService {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * 
   * @param orderId
   * @return true if the reservation can be confirmed for all order items.
   */
  public boolean reserveOrderItems(Long orderId) {
    
    List<Object[]> itemsForOrder = getItemsForOrder(orderId);
    
    boolean orderConfirmed = true;
    
    for (Object[] objects : itemsForOrder) {
      OrderItem oi = (OrderItem) objects[0];
      InventoryItem ii = (InventoryItem) objects[1];
      
      int available = ii.getAvailable();
      int reserved = ii.getReserved();
      int orderedAmount = oi.getAmount();
      
      if( (available - reserved) < orderedAmount ) {
        // the item is out of stock
        oi.setReservationStatus(OrderItem.STATUS_UNCONFIRMED);
        orderConfirmed = false;
        
      } else {
        // reserve the ordered items
        ii.setReserved(reserved + orderedAmount);
        oi.setReservationStatus(OrderItem.STATUS_CONFIRMED);
      }        
    }
    
    return orderConfirmed;
    
  }
  
  public void releaseReservation(Long orderId) {
	  
	  List<Object[]> itemsForOrder = getItemsForOrder(orderId);
	  
	  for (Object[] objects : itemsForOrder) {
	      OrderItem oi = (OrderItem) objects[0];
	      InventoryItem ii = (InventoryItem) objects[1];
	      
	      int reserved = ii.getReserved();
	      int orderedAmount = oi.getAmount();
	      ii.setReserved(reserved + orderedAmount);	      
	  }
  }

  /**
   * 
   * @param orderId
   * @return List<Object[]> where 
   *    Object[0] is of type OrderItem  
   *    Object[1] is of type InventoryItem
   */
  @SuppressWarnings("unchecked")
  protected List<Object[]> getItemsForOrder(Long orderId) {
    
    return entityManager.createQuery(
        "SELECT oi, ii from Order o " +
          "join o.orderItems oi join oi.article a join a.inventoryItem ii " +
        "WHERE o.id=:orderId ")
      .setParameter("orderId", orderId)
      .getResultList();
    
  }
}
