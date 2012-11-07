package com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.camunda.fox.showcase.fruitshop.order.entity.OrderItem;

public class OrderItemDTO {
  
  private long articleId = -1;

  private int amount = -1;

  public OrderItemDTO() {
  }

  public OrderItemDTO(OrderItem item) {
    this.amount = item.getAmount();
    this.articleId = item.getArticle().getId();
  }

  
  public int getAmount() {
    return amount;
  }

  public long getArticleId() {
    return articleId;
  }

  public void setArticleId(long articleId) {
    this.articleId = articleId;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  // static helpers /////////////////////////////
  
  public static List<OrderItemDTO> wrapAll(List<OrderItem> orderItems) {
    ArrayList<OrderItemDTO> result = new ArrayList<OrderItemDTO>();
    
    for (OrderItem orderItem : orderItems) {
      result.add(OrderItemDTO.wrap(orderItem));
    }
    
    return result;
  }
  
  private static OrderItemDTO wrap(OrderItem orderItem) {
    return new OrderItemDTO(orderItem);
  }
}
