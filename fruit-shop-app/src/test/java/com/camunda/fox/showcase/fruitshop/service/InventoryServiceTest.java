package com.camunda.fox.showcase.fruitshop.service;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;
import com.camunda.fox.showcase.fruitshop.inventory.boundary.InventoryService;
import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;
import com.camunda.fox.showcase.fruitshop.inventory.entity.InventoryItem;
import com.camunda.fox.showcase.fruitshop.order.entity.Order;
import com.camunda.fox.showcase.fruitshop.order.entity.OrderItem;


@RunWith(Arquillian.class)
public class InventoryServiceTest {
  
  @Deployment
  public static WebArchive deployment() {    
    return ShrinkWrap.create(WebArchive.class)
      .addPackage(AbstractEntity.class.getPackage())
      .addClass(InventoryService.class)
      .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    
  }
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Inject
  private UserTransaction utx;
  
  @Inject 
  private InventoryService inventoryService;
  
  @Test
  public void testReserveOrderItems() throws Exception {
    
    Order o1 = new Order();
    o1.setCustomer("Customer");
    
    try {
      
      utx.begin();
      
      Article a1 = new Article();
      a1.setName("A1");
      InventoryItem ia1 = new InventoryItem(a1, 10);
      
      entityManager.persist(a1);
      entityManager.persist(ia1);
      
      Article a2 = new Article();
      a1.setName("A2");
      InventoryItem ia2 = new InventoryItem(a2, 5);
      
      entityManager.persist(a2);
      entityManager.persist(ia2);
      
      OrderItem oi1 = new OrderItem();
      oi1.setAmount(5);
      oi1.setArticle(a1);
      o1.getOrderItems().add(oi1);
      
      entityManager.persist(o1);      
      
      utx.commit();
    } catch (RuntimeException e) {
      utx.rollback();
      throw e;
    }
    
    // perform the reservation
    inventoryService.reserveOrderItems(o1.getId());
    
    try {
      utx.begin();
      
      o1 = entityManager.find(Order.class, o1.getId());
      List<OrderItem> orderItems = o1.getOrderItems();
      for (OrderItem oi : orderItems) {
        InventoryItem ii = oi.getArticle().getInventoryItem();
        Assert.assertEquals(5, ii.getReserved());
      }
      
      utx.commit();
    } catch (RuntimeException e) {
      utx.rollback();
      throw e;
    }
      
    
  }

}
