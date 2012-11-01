package com.camunda.fox.showcase.fruitshop.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.activiti.cdi.BusinessProcess;

import com.camunda.fox.showcase.fruitshop.entity.Article;
import com.camunda.fox.showcase.fruitshop.entity.Order;
import com.camunda.fox.showcase.fruitshop.entity.OrderItem;
import com.camunda.fox.showcase.fruitshop.repository.ArticleRepository;
import com.camunda.fox.showcase.fruitshop.repository.OrderRepository;
import com.camunda.fox.showcase.fruitshop.web.dto.OrderDTO;
import com.camunda.fox.showcase.fruitshop.web.dto.OrderItemDTO;

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
    
    businessProcess.setVariable("orderId", order.getId());
    String pid = businessProcess.startProcessByMessage("New Order").getId();
    
    order.setProcessInstanceId(pid);
    
    return String.valueOf(order.getId());

  }

  protected Integer getAmount(OrderItemDTO newOrderItem) {
    if(newOrderItem.getAmount() == null) {
      throw new RuntimeException("amount cannot be null");
    }
    
    Integer amount = Integer.parseInt(newOrderItem.getAmount());
    
    if(amount <= 0) {
      throw new RuntimeException("amount must be greater than '0'");
    }
    
    return amount;
  }

  protected Article getArticle(OrderItemDTO newOrderItem) {
    
    if(newOrderItem.getArticleId() == null) {
      throw new RuntimeException("article ID cannot be null.");
    }
    
    Long articleId = Long.valueOf(newOrderItem.getArticleId());
    
    Article article = articleRepository.findById(articleId);
    if (article == null) {
      throw new RuntimeException("No article for ID '" + articleId + "' found.");
    }
    
    return article;
  }
}
