package com.camunda.fox.showcase.fruitshop.order.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.activiti.cdi.BusinessProcess;

import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;
import com.camunda.fox.showcase.fruitshop.inventory.repository.ArticleRepository;
import com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto.OrderDTO;
import com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto.OrderItemDTO;
import com.camunda.fox.showcase.fruitshop.order.entity.Order;
import com.camunda.fox.showcase.fruitshop.order.entity.OrderItem;
import com.camunda.fox.showcase.fruitshop.order.entity.OrderUpdate;
import com.camunda.fox.showcase.fruitshop.order.process.ProcessOrder;
import com.camunda.fox.showcase.fruitshop.order.repository.OrderRepository;

@Stateless 
public class OrderService {

  @Inject
  private OrderRepository orderRepository;

  @Inject
  private ArticleRepository articleRepository;

  @Inject
  private BusinessProcess businessProcess;

  public String newOrder(OrderDTO newOrder) {

    Order order = new Order();
    order.setCustomer(newOrder.getCustomer());
    
    List<OrderItem> orderItems = order.getOrderItems();
    for (OrderItemDTO newOrderItem : newOrder.getOrderItems()) {
      
      Article article = getArticle(newOrderItem);
      Integer amount = getAmount(newOrderItem);
      
      OrderItem orderItem = new OrderItem();
      orderItem.setAmount(amount);
      orderItem.setArticle(article);
      orderItems.add(orderItem);
    }
    
    orderRepository.saveAndFlush(order);
    
    businessProcess.setVariable(ProcessOrder.VARNAME_ORDER_ID, order.getId());
    String pid = businessProcess.startProcessByMessage("New Order").getId();
    
    order.setProcessInstanceId(pid);
    
    return String.valueOf(order.getId());

  }

  protected Integer getAmount(OrderItemDTO newOrderItem) {
    if (newOrderItem.getAmount() == -1) {
      throw new RuntimeException("amount cannot be null");
    }
    
    int amount = newOrderItem.getAmount();
    
    if (amount <= 0) {
      throw new RuntimeException("amount must be greater than '0'");
    }
    
    return amount;
  }

  protected Article getArticle(OrderItemDTO newOrderItem) {
    
    if (newOrderItem.getArticleId() == -1) {
      throw new RuntimeException("article ID cannot be null.");
    }
    
    Long articleId = Long.valueOf(newOrderItem.getArticleId());
    
    Article article = articleRepository.findById(articleId);
    if (article == null) {
      throw new RuntimeException("No article for ID '" + articleId + "' found.");
    }
    
    return article;
  }

  public Order getOrder(long id) {
    return orderRepository.findByIdFetchOrderItems(id);
  }

  public List<OrderUpdate> getOrderUpdates(long id) {
    return orderRepository.findOrderUpdatesByOrderId(id);
  }
}
