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
  
  public void reserveOrderItems(Long orderId) {
    
    List<Object[]> result = getItemsForOrder(orderId);
    
    for (Object[] objects : result) {
      OrderItem oi = (OrderItem) objects[0];
      InventoryItem ii = (InventoryItem) objects[1];
      
      ii.reserve(oi);           
    }
    
  }
  
  public void releaseReservation(Long orderId) {
    
    List<Object[]> result = getItemsForOrder(orderId);
    
    for (Object[] objects : result) {
      OrderItem oi = (OrderItem) objects[0];
      InventoryItem ii = (InventoryItem) objects[1];
      
      ii.release(oi);           
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
