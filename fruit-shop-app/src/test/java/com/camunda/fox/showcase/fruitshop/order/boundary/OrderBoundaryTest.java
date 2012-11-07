package com.camunda.fox.showcase.fruitshop.order.boundary;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.activiti.engine.RuntimeService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.camunda.fox.showcase.fruitshop.DeploymentHelper;
import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;
import com.camunda.fox.showcase.fruitshop.inventory.entity.InventoryItem;
import com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto.OrderDTO;
import com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto.OrderItemDTO;


@RunWith(Arquillian.class)
public class OrderBoundaryTest {
  
  @Deployment
  public static WebArchive deployment() {    
    return ShrinkWrap.create(WebArchive.class)
      .addPackages(true, "com.camunda.fox.showcase.fruitshop")
      // add fox platform client as lib
      .addAsLibrary(DeploymentHelper.getFoxPlatformClient())
      .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
      .addAsResource("META-INF/processes.xml", "META-INF/processes.xml")
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addAsResource("processOrder.bpmn");
    
  }
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Inject
  private UserTransaction utx;
  
  @Inject 
  private OrderService orderService;
  
  @Inject
  private RuntimeService runtimeService;
  
  @Test
  public void testReserveOrderItems() throws Exception {
    
    Article a1 = new Article();
    a1.setName("A1");
    InventoryItem ia1 = new InventoryItem(a1, 10);
    
    Article a2 = new Article();
    a1.setName("A2");
    InventoryItem ia2 = new InventoryItem(a2, 5);

    try {
      
      utx.begin();
      
      entityManager.persist(a1);
      entityManager.persist(ia1);
      
      entityManager.persist(a2);
      entityManager.persist(ia2);
      
      utx.commit();
    } catch (RuntimeException e) {
      utx.rollback();
      throw e;
    }
    
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setCusomer("Test");
    OrderItemDTO orderItem = new OrderItemDTO();
    orderItem.setAmount(5);
    orderItem.setArticleId(a1.getId());
    orderDTO.getOrderItems().add(orderItem);
    
    String newOrderId = orderService.newOrder(orderDTO);
        
    Thread.sleep(5000);
    
    Assert.assertEquals(1, runtimeService.createProcessInstanceQuery().count());
    
  }

}
